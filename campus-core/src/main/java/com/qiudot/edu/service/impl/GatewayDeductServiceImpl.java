/*
 * qiudot.com Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by qiudot
 * date:2018-10-08
 */
package com.qiudot.edu.service.impl;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.core.utils.enums.ResultStatus;
import com.qiudot.edu.dao.GatewayDeductDao;
import com.qiudot.edu.entity.GatewayDeduct;
import com.qiudot.edu.enums.ChannelTypeEnum;
import com.qiudot.edu.enums.NotifyStatusEnum;
import com.qiudot.edu.enums.StatusEnum;
import com.qiudot.edu.service.AliInterfaceService;
import com.qiudot.edu.service.GatewayDeductService;
import com.qiudot.edu.service.base.dto.GatewayDeductPayDto;
import com.qiudot.edu.service.base.dto.GatewayTradeQueryDto;
import com.qiudot.edu.service.base.result.AliDeductPayResult;
import com.qiudot.edu.service.base.result.AliTradeQueryResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 网关代扣记录 Service实现
 * <p>
 * Date: 2018-10-08 14:25:30
 *
 * @author qiudot
 */
@Service("gatewayDeductService")
@Slf4j
public class GatewayDeductServiceImpl extends EntityServiceImpl<GatewayDeduct, GatewayDeductDao> implements GatewayDeductService {

    @Autowired
    private AliInterfaceService aliInterfaceService;

    @Override
    public AliDeductPayResult deductPay(GatewayDeductPayDto gatewayDeductPayDto) {
        //创建网关订单并保存
        GatewayDeduct gatewayDeduct = convertGatewayOrder(gatewayDeductPayDto);
        //发送到支付宝并更新订单
        AliDeductPayResult result = aliInterfaceService.deductPay(gatewayDeductPayDto);
        gatewayDeduct.setBankRespMessage(result.getBankRespMessage());
        if (result.success() && result.getTradeStatus().equals(ResultStatus.success)) {
            gatewayDeduct.setNotifyStatus(NotifyStatusEnum.SUCCESS);
            gatewayDeduct.setStatus(StatusEnum.BS);
            gatewayDeduct.setBankOrderNo(result.getBankOrderNo());
            gatewayDeduct.setNotifyStatus(NotifyStatusEnum.SUCCESS);
            gatewayDeduct.setNotifyDate(new Date());
            gatewayDeduct.setMemo("代扣同步成功");
        } else if (result.getStatus().equals(ResultStatus.processing) && result.getTradeStatus().equals(ResultStatus.processing)) {
            gatewayDeduct.setStatus(StatusEnum.BP);
            gatewayDeduct.setMemo("代扣同步处理中");
        } else {
            gatewayDeduct.setStatus(StatusEnum.BF);
            gatewayDeduct.setNotifyStatus(NotifyStatusEnum.SUCCESS);
            gatewayDeduct.setNotifyDate(new Date());
            gatewayDeduct.setMemo("代扣同步失败");
        }
        this.save(gatewayDeduct);
        return result;
    }

    @Override
    public AliTradeQueryResult tradeQuery(GatewayTradeQueryDto gatewayTradeQueryDto) {
        AliTradeQueryResult result = new AliTradeQueryResult();
        GatewayDeduct gatewayDeduct = this.getEntityDao().getEntityByBizOrderNo(gatewayTradeQueryDto.getBizOrderNo());
        //校验原订单是否存在
        if (gatewayDeduct == null) {
            log.info("原代扣订单号bizOrderNo={}在网关表中不存在", gatewayTradeQueryDto.getBizOrderNo());
            result.markProcessing();
            result.setTradeStatus(ResultStatus.processing);
            return result;
        }
        //校验原订单是否在网关表中是最终状态
        if (gatewayDeduct.getStatus().equals(StatusEnum.BS) || gatewayDeduct.getStatus().equals(StatusEnum.BF)) {
            log.info("原代扣订单号bizOrderNo={}在网关表中已为最终状态status={}", gatewayTradeQueryDto.getBizOrderNo(), gatewayDeduct.getStatus().getCode());
            if (gatewayDeduct.getStatus().equals(StatusEnum.BS)) {
                result.setStatus(ResultStatus.success);
                result.setTradeStatus(ResultStatus.success);
            } else if (gatewayDeduct.getStatus().equals(StatusEnum.BF)) {
                result.setStatus(ResultStatus.failure);
                result.setTradeStatus(ResultStatus.failure);
            }
            return result;
        }
        //调用支付宝订单查询接口
        result = aliInterfaceService.tradeQuery(gatewayTradeQueryDto);
        gatewayDeduct.setBankRespMessage(result.getBankRespMessage());
        if (result.success() && result.getTradeStatus().equals(ResultStatus.success)) {
            gatewayDeduct.setNotifyStatus(NotifyStatusEnum.SUCCESS);
            gatewayDeduct.setStatus(StatusEnum.BS);
            gatewayDeduct.setBankOrderNo(result.getBankOrderNo());
            gatewayDeduct.setNotifyStatus(NotifyStatusEnum.SUCCESS);
            gatewayDeduct.setNotifyDate(new Date());
            gatewayDeduct.setMemo("代扣查询结果为成功");
        } else if (result.getStatus().equals(ResultStatus.processing) && result.getTradeStatus().equals(ResultStatus.processing)) {
            gatewayDeduct.setStatus(StatusEnum.BP);
            gatewayDeduct.setMemo("代扣查询结果为处理中");
        } else {
            gatewayDeduct.setStatus(StatusEnum.BF);
            gatewayDeduct.setNotifyStatus(NotifyStatusEnum.SUCCESS);
            gatewayDeduct.setNotifyDate(new Date());
            gatewayDeduct.setMemo("代扣查询结果为失败");
        }
        //更新流水表
        this.update(gatewayDeduct);
        return result;
    }

    /**
     * 创建并保存代扣订单
     *
     * @param deductPayDto
     */
    private GatewayDeduct convertGatewayOrder(GatewayDeductPayDto deductPayDto) {
        GatewayDeduct gatewayDeduct = new GatewayDeduct();
        gatewayDeduct.setPartnerId(deductPayDto.getPartnerId());
        gatewayDeduct.setAmount(deductPayDto.getAmount().getCent());
        gatewayDeduct.setBizOrderNo(deductPayDto.getBizOrderNo());
        gatewayDeduct.setStatus(StatusEnum.INIT);
        gatewayDeduct.setChannelApi("alipay.trade.pay");
        gatewayDeduct.setChannelType(ChannelTypeEnum.ALI);
        gatewayDeduct.setBatchNo(deductPayDto.getBizOrderNo());
        gatewayDeduct.setNotifyDate(new Date());
        gatewayDeduct.setUserNo(deductPayDto.getUserNo());
        gatewayDeduct.setNotifyStatus(NotifyStatusEnum.INIT);
        gatewayDeduct.setNotifySendCount(0);
        gatewayDeduct.setUserIp(deductPayDto.getUserIp());
        return gatewayDeduct;
    }
}

/*
 *
 * www.qiudot.com Inc.
 * Copyright (c) 2018  All Rights Reserved
 */

package com.qiudot.edu.event.handler;

import com.acooly.core.utils.enums.ResultStatus;
import com.acooly.core.utils.mapper.BeanCopier;
import com.acooly.core.utils.system.IPUtil;
import com.acooly.module.event.EventHandler;
import com.qiudot.edu.entity.Trade;
import com.qiudot.edu.enums.GatewayStautsEnum;
import com.qiudot.edu.enums.TradeStatusEnum;
import com.qiudot.edu.event.DeductPayEvent;
import com.qiudot.edu.event.DeductPayQueryEvent;
import com.qiudot.edu.service.GatewayDeductService;
import com.qiudot.edu.service.TradeService;
import com.qiudot.edu.service.base.dto.GatewayDeductPayDto;
import com.qiudot.edu.service.base.dto.GatewayTradeQueryDto;
import com.qiudot.edu.service.base.result.AliDeductPayResult;
import com.qiudot.edu.service.base.result.AliTradeQueryResult;
import lombok.extern.slf4j.Slf4j;
import net.engio.mbassy.listener.Handler;
import net.engio.mbassy.listener.Invoke;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 发送短信/消息事件封装
 * Created by zhike@qiudot.com on 2018-08-07 19:11.
 */

@EventHandler
@Slf4j
public class DeductPayEventHandler {

    @Autowired
    private GatewayDeductService gatewayDeductService;

    @Autowired
    private TradeService tradeService;

    /**
     * 发送代扣充值
     *
     * @param event
     */
    @Handler(delivery = Invoke.Asynchronously)
    public void sendDeductPayHandle(DeductPayEvent event) {
        log.info("接收...........支付宝代扣事件:{}", event);
        GatewayDeductPayDto gatewayDeductPayDto = new GatewayDeductPayDto();
        BeanCopier.copy(event, gatewayDeductPayDto);
        gatewayDeductPayDto.setUserIp(IPUtil.getFirstNoLoopbackIPV4Address());
        AliDeductPayResult result = gatewayDeductService.deductPay(gatewayDeductPayDto);
        Trade trade = tradeService.getEntityByBizOrderNo(event.getBizOrderNo());
        trade.setTradeTime(new Date());
        if (result.success() && result.getTradeStatus().equals(ResultStatus.success)) {
            trade.setTradeStatus(TradeStatusEnum.SUCCESS);
            trade.setGatewayStauts(GatewayStautsEnum.SUCCESS);
            trade.setMemo("同步代扣处理中");
        } else if (result.getStatus().equals(ResultStatus.processing) && result.getTradeStatus().equals(ResultStatus.processing)) {
            trade.setTradeStatus(TradeStatusEnum.PROCESSING);
            trade.setGatewayStauts(GatewayStautsEnum.PROCESSING);
            trade.setMemo("同步代扣处理中");
        } else {
            trade.setTradeStatus(TradeStatusEnum.FAILURE);
            trade.setGatewayStauts(GatewayStautsEnum.FAILURE);
            trade.setMemo("同步代扣失败");
        }
        tradeService.update(trade);
    }

    /**
     * 发送代扣订单查询
     *
     * @param event
     */
    @Handler(delivery = Invoke.Asynchronously)
    public void sendDeductQueryHandle(DeductPayQueryEvent event) {
        log.info("接收...........支付宝代扣订单查询事件:{}", event);
        GatewayTradeQueryDto gatewayTradeQueryDto = new GatewayTradeQueryDto();
        BeanCopier.copy(event, gatewayTradeQueryDto);
        AliTradeQueryResult result = gatewayDeductService.tradeQuery(gatewayTradeQueryDto);
        Trade trade = tradeService.getEntityByBizOrderNo(event.getBizOrderNo());
        if (result.success() && result.getTradeStatus().equals(ResultStatus.success)) {
            trade.setTradeStatus(TradeStatusEnum.SUCCESS);
            trade.setGatewayStauts(GatewayStautsEnum.SUCCESS);
            trade.setMemo("代扣查询结果为成功");
        } else if (result.getStatus().equals(ResultStatus.processing) && result.getTradeStatus().equals(ResultStatus.processing)) {
            trade.setTradeStatus(TradeStatusEnum.PROCESSING);
            trade.setGatewayStauts(GatewayStautsEnum.PROCESSING);
            trade.setMemo("代扣查询结果为处理中");
        } else {
            trade.setTradeStatus(TradeStatusEnum.FAILURE);
            trade.setGatewayStauts(GatewayStautsEnum.FAILURE);
            trade.setMemo("代扣查询结果为失败");
        }
        tradeService.update(trade);
    }
}

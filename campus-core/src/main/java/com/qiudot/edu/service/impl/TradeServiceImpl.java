/*
 * qiudot.com Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by qiudot
 * date:2018-10-08
 */
package com.qiudot.edu.service.impl;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.facade.ResultBase;
import com.acooly.core.common.facade.ResultCode;
import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.core.utils.Ids;
import com.acooly.core.utils.Money;
import com.acooly.core.utils.Strings;
import com.acooly.core.utils.enums.ResultStatus;
import com.acooly.core.utils.system.IPUtil;
import com.acooly.core.utils.validate.Validators;
import com.acooly.module.event.EventBus;
import com.qiudot.edu.dao.TradeDao;
import com.qiudot.edu.dto.CustomerCardAuthInfo;
import com.qiudot.edu.entity.CustomerCard;
import com.qiudot.edu.entity.Trade;
import com.qiudot.edu.enums.GatewayStautsEnum;
import com.qiudot.edu.enums.TradeStatusEnum;
import com.qiudot.edu.enums.TradeTypeEnum;
import com.qiudot.edu.event.DeductPayEvent;
import com.qiudot.edu.event.DeductPayQueryEvent;
import com.qiudot.edu.service.CustomerCardService;
import com.qiudot.edu.service.TradeService;
import com.qiudot.edu.service.WhdcInterfaceService;
import com.qiudot.edu.service.base.dto.TradeDeductPayDto;
import com.qiudot.edu.service.base.order.PageQueryTradeDetailsOrder;
import com.qiudot.edu.service.base.order.WhdcGetPersonDetailsOrder;
import com.qiudot.edu.service.base.result.PageQueryTradeOrderDetailsResult;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 交易记录 Service实现
 * <p>
 * Date: 2018-10-08 14:25:30
 *
 * @author qiudot
 */
@Service("tradeService")
@Slf4j
public class TradeServiceImpl extends EntityServiceImpl<Trade, TradeDao> implements TradeService {

    @Autowired
    private CustomerCardService customerCardService;

    @Autowired
    private EventBus eventBus;

    @Autowired
    private WhdcInterfaceService whdcInterfaceService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultBase deductPay(TradeDeductPayDto deductPayDto) {
        MDC.put("bizOrderNo", deductPayDto.getBizOrderNo());
        //校验参数
        checkOrder(deductPayDto);
        ResultBase result = new ResultBase();
        //校验此卡号是否可以进行交易
        checkTrade(deductPayDto);
        //创建并保存交易订单
        try {
            Trade trade = convertTradeOrder(deductPayDto);
            //设置结果
            result.setDetail("代扣受理成功");
            result.setCode(ResultCode.SUCCESS.getCode());
            result.setStatus(ResultStatus.success);
            //发送异步事件到网关进行代扣
            eventBus.publishAfterTransactionCommitted(new DeductPayEvent(deductPayDto.getPartnerId(), deductPayDto.getAmount(), trade.getSchoolAnthCode(), trade.getAgreementNo(), deductPayDto.getSubject(), deductPayDto.getBizOrderNo(), deductPayDto.getUserNo()));
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                log.info("此商户订单号merchOrderNo={}已经存在，进行幂等性校验",deductPayDto.getMerchOrderNo());
                //幂等性校验
                Trade oldTrade = this.getEntityDao().getEntityByMerchOrderNo(deductPayDto.getMerchOrderNo(),deductPayDto.getPartnerId());
                if (oldTrade != null) {
                    if (oldTrade.getTradeStatus().equals(TradeStatusEnum.SUCCESS) || oldTrade.getTradeStatus().equals(TradeStatusEnum.SUCCESS)) {
                        //设置结果
                        result.setDetail("代扣成功");
                        result.setCode(ResultCode.SUCCESS.getCode());
                        result.setStatus(ResultStatus.success);
                    } else {
                        //设置结果
                        result.setDetail("代扣失败");
                        result.setCode(ResultCode.FAILURE.getCode());
                        result.setStatus(ResultStatus.failure);
                    }
                    return result;
                }
            } else {
                throw e;
            }
        }
        return result;
    }

    @Override
    public Trade getEntityByBizOrderNo(String bizOrderNo) {
        return this.getEntityDao().getEntityByBizOrderNo(bizOrderNo);
    }

    @Override
    public void querySendDeductPay() {
        List<Trade> needDeductOrders = this.getEntityDao().queryNeedDeductPayDatas();
        for (Trade trade : needDeductOrders) {
            //判断是否需要重新生成bizOrderNo
            if (trade.getGatewayStauts().equals(GatewayStautsEnum.FAILURE)) {
                trade.setBizOrderNo(Ids.oid());
                this.getEntityDao().update(trade);
            }
            //发送代扣事件
            eventBus.publishAfterTransactionCommitted(new DeductPayEvent(trade.getPartnerId(), Money.cent(trade.getAmount()), trade.getAgreementNo(), trade.getSchoolAnthCode(), trade.getComment(), trade.getBizOrderNo(), trade.getCustomerUserNo()));
        }
    }


    @Override
    public void querySendDeductPayQuery() {
        List<Trade> needDeductQueryOrders = this.getEntityDao().queryNeedDeductPayQueryDatas();
        needDeductQueryOrders.forEach(trade -> {
            //发送代扣查询事件
            eventBus.publishAfterTransactionCommitted(new DeductPayQueryEvent(trade.getBizOrderNo()));
        });
    }

    @Override
    public PageQueryTradeOrderDetailsResult pageQueryTradeOrderDetails(PageQueryTradeDetailsOrder order) {
        WhdcGetPersonDetailsOrder whdcGetPersonDetailsOrder = new WhdcGetPersonDetailsOrder();
        whdcGetPersonDetailsOrder.setPageInfo(new PageInfo());
        whdcGetPersonDetailsOrder.getPageInfo().setCurrentPage(order.getPageInfo().getCurrentPage());
        whdcGetPersonDetailsOrder.getPageInfo().setCountOfCurrentPage(order.getPageInfo().getCountOfCurrentPage());
        whdcGetPersonDetailsOrder.setBeginDate(order.getBeginDate());
        if (Strings.isNotBlank(order.getCardNo())) {
            whdcGetPersonDetailsOrder.setPersonno(order.getCardNo());
            whdcGetPersonDetailsOrder.setUsercode(order.getSchoolNo());
        } else {
            //获取当前用户最新绑定的一张卡
            CustomerCard customerCard = customerCardService.getLastBindCardByUserNo(order.getUserNo());
            whdcGetPersonDetailsOrder.setPersonno(customerCard.getCardNo());
            whdcGetPersonDetailsOrder.setUsercode(customerCard.getShoolCode());
        }
        whdcGetPersonDetailsOrder.setEndDate(order.getEndDate());
        whdcGetPersonDetailsOrder.setBegintime(order.getBegintime());
        whdcGetPersonDetailsOrder.setEndtime(order.getEndtime());
        PageQueryTradeOrderDetailsResult result = whdcInterfaceService.queryTradeOrderDetails(whdcGetPersonDetailsOrder);
        return result;
    }

    /**
     * 创建并保存代扣订单
     *
     * @param deductPayDto
     */
    private Trade convertTradeOrder(TradeDeductPayDto deductPayDto) {
        CustomerCardAuthInfo customerCard = customerCardService.getCardAuthInfoByCardNo(deductPayDto.getCardNo(), deductPayDto.getSchoolNo());
        Trade trade = new Trade();
        trade.setPartnerId(deductPayDto.getPartnerId());
        trade.setAmount(deductPayDto.getAmount().getCent());
        trade.setBizOrderNo(deductPayDto.getBizOrderNo());
        trade.setCardNo(deductPayDto.getCardNo());
        trade.setTradeStatus(TradeStatusEnum.PROCESSING);
        trade.setGatewayStauts(GatewayStautsEnum.INIT);
        trade.setTradeType(TradeTypeEnum.ONLINE);
        trade.setMerchOrderNo(deductPayDto.getMerchOrderNo());
        trade.setCustomerUserNo(customerCard.getUserNo());
        trade.setComment(deductPayDto.getSubject());
        trade.setAgreementNo(customerCard.getAgreementNo());
        trade.setSchoolAnthCode(customerCard.getAliAuthCode());
        trade.setSchoolNo(deductPayDto.getSchoolNo());
        trade.setUserIp(IPUtil.getFirstNoLoopbackIPV4Address());
        this.save(trade);
        return trade;
    }

    /**
     * 校验当前用户是否可以进行交易
     *
     * @param deductPayDto
     */
    private void checkTrade(TradeDeductPayDto deductPayDto) {
        Trade trade = this.getEntityDao().getLastEntityByCardNoAndSchoolNo(deductPayDto.getCardNo(), deductPayDto.getSchoolNo(), deductPayDto.getMerchOrderNo(), deductPayDto.getPartnerId());
        if (trade != null && !trade.getTradeStatus().equals(TradeStatusEnum.SUCCESS)) {
            log.info("此卡号cardNo={}上一笔交易还没成功，不允许继续进行交易");
            throw new BusinessException(ResultCode.FAILURE, "此卡号上一笔交易还没有成功，不允许继续交易");
        }
    }

    /**
     * 校验参数
     *
     * @param deductPayDto
     */
    private void checkOrder(TradeDeductPayDto deductPayDto) {
        Validators.assertJSR303(deductPayDto);
    }
}

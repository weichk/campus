/*
 * qiudot.com Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by qiudot
 * date:2018-10-08
 *
 */
package com.qiudot.edu.service;

import com.acooly.core.common.facade.ResultBase;
import com.acooly.core.common.service.EntityService;
import com.qiudot.edu.entity.Trade;
import com.qiudot.edu.service.base.dto.TradeDeductPayDto;
import com.qiudot.edu.service.base.order.PageQueryTradeDetailsOrder;
import com.qiudot.edu.service.base.result.PageQueryTradeOrderDetailsResult;

/**
 * 交易记录 Service接口
 *
 * Date: 2018-10-08 14:25:30
 * @author qiudot
 *
 */
public interface TradeService extends EntityService<Trade> {

    /**
     * 代扣
     * @param deductPayDto
     * @return
     */
    ResultBase deductPay(TradeDeductPayDto deductPayDto);

    /**
     * 根据订单号查询订单信息
     * @param bizOrderNo
     * @return
     */
    Trade getEntityByBizOrderNo(String bizOrderNo);

    /**
     * 查询需要代扣的数据发送到支付宝进行代扣
     */
    void querySendDeductPay();

    /**
     * 查询需要做代扣查询的数据发送到支付宝进行代扣查询
     */
    void querySendDeductPayQuery();

    /**
     * 根据用户ID分页查询用户交易流水
     * @param order
     * @return
     */
    PageQueryTradeOrderDetailsResult pageQueryTradeOrderDetails(PageQueryTradeDetailsOrder order);
}

/*
 * qiudot.com Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by qiudot
 * date:2018-10-08
 *
 */
package com.qiudot.edu.service;

import com.acooly.core.common.service.EntityService;
import com.qiudot.edu.entity.GatewayDeduct;
import com.qiudot.edu.service.base.dto.GatewayDeductPayDto;
import com.qiudot.edu.service.base.dto.GatewayTradeQueryDto;
import com.qiudot.edu.service.base.result.AliDeductPayResult;
import com.qiudot.edu.service.base.result.AliTradeQueryResult;

/**
 * 网关代扣记录 Service接口
 *
 * Date: 2018-10-08 14:25:30
 * @author qiudot
 *
 */
public interface GatewayDeductService extends EntityService<GatewayDeduct> {

    /**
     * 代扣充值
     * @return
     */
    AliDeductPayResult deductPay(GatewayDeductPayDto gatewayDeductPayDto);

    /**
     * 交易订单查询
     * @param gatewayTradeQueryDto
     * @return
     */
    AliTradeQueryResult tradeQuery(GatewayTradeQueryDto gatewayTradeQueryDto);
}

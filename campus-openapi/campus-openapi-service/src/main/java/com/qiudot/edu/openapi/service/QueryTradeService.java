package com.qiudot.edu.openapi.service;

import com.acooly.openapi.framework.common.annotation.OpenApiNote;
import com.acooly.openapi.framework.common.annotation.OpenApiService;
import com.acooly.openapi.framework.common.enums.ResponseType;
import com.acooly.openapi.framework.core.service.base.BaseApiService;
import com.qiudot.edu.openapi.message.QueryTradeReq;
import com.qiudot.edu.openapi.message.QueryTradeResp;
import lombok.extern.slf4j.Slf4j;

/**
 * @author qiuboboy@qq.com
 * @date 2018-10-19 15:29
 */
@OpenApiNote("交易记录查询")
@Slf4j
@OpenApiService(name = "queryTrade", desc = "交易记录查询", responseType = ResponseType.SYN, owner = "qiubo")
public class QueryTradeService extends BaseApiService<QueryTradeReq, QueryTradeResp> {
    @Override
    protected void doService(QueryTradeReq request, QueryTradeResp response) {

    }
}

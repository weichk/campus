package com.qiudot.edu.openapi.service;

import com.acooly.openapi.framework.common.annotation.OpenApiNote;
import com.acooly.openapi.framework.common.annotation.OpenApiService;
import com.acooly.openapi.framework.common.enums.ResponseType;
import com.acooly.openapi.framework.common.message.ApiResponse;
import com.acooly.openapi.framework.core.service.base.BaseApiService;
import com.qiudot.edu.openapi.message.CampusReq;
import lombok.extern.slf4j.Slf4j;

/**
 * @author qiuboboy@qq.com
 * @date 2018-10-19 15:08
 */
@OpenApiNote("一卡通支付宝解绑")
@Slf4j
@OpenApiService(name = "unbindingCard", desc = "一卡通解绑", responseType = ResponseType.SYN, owner = "qiubo")
public class UnBindingCardService extends BaseApiService<CampusReq, ApiResponse> {
    @Override
    protected void doService(CampusReq request, ApiResponse response) {

    }
}

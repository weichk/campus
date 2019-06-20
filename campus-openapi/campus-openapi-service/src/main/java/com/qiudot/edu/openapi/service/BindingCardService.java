package com.qiudot.edu.openapi.service;

import com.acooly.openapi.framework.common.annotation.OpenApiNote;
import com.acooly.openapi.framework.common.annotation.OpenApiService;
import com.acooly.openapi.framework.common.enums.ResponseType;
import com.acooly.openapi.framework.common.message.ApiResponse;
import com.acooly.openapi.framework.core.service.base.BaseApiService;
import com.qiudot.edu.openapi.message.BindingCardReq;
import lombok.extern.slf4j.Slf4j;

/**
 * @author qiuboboy@qq.com
 * @date 2018-10-19 15:08
 */
@OpenApiNote("一卡通支付宝绑卡")
@Slf4j
@OpenApiService(name = "bindingCard", desc = "一卡通绑卡", responseType = ResponseType.SYN, owner = "qiubo")
public class BindingCardService extends BaseApiService<BindingCardReq, ApiResponse> {
    @Override
    protected void doService(BindingCardReq request, ApiResponse response) {

    }
}

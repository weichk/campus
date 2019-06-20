package com.qiudot.edu.openapi.service;

import com.acooly.openapi.framework.common.annotation.OpenApiNote;
import com.acooly.openapi.framework.common.annotation.OpenApiService;
import com.acooly.openapi.framework.common.enums.ResponseType;
import com.acooly.openapi.framework.core.service.base.BaseApiService;
import com.qiudot.edu.openapi.message.CampusReq;
import com.qiudot.edu.openapi.message.QueryUserInfoResp;
import lombok.extern.slf4j.Slf4j;

/**
 * @author qiuboboy@qq.com
 * @date 2018-10-19 14:39
 */
@OpenApiNote("查询用户信息")
@Slf4j
@OpenApiService(name = "queryUserInfo", desc = "查询用户信息", responseType = ResponseType.SYN, owner = "qiubo")
public class QueryUserInfoService extends BaseApiService<CampusReq, QueryUserInfoResp> {
    @Override
    protected void doService(CampusReq request, QueryUserInfoResp response) {

    }
}

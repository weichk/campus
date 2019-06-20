package com.qiudot.edu.openapi.message;

import com.acooly.openapi.framework.common.annotation.OpenApiField;
import com.acooly.openapi.framework.common.message.ApiRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

/**
 * @author qiuboboy@qq.com
 * @date 2018-10-19 15:30
 */
@Getter
@Setter
@Slf4j
public class CampusReq extends ApiRequest {
    @NotEmpty
    @Size(max = 32)
    @OpenApiField(desc = "学校编码", constraint = "学校编码", demo = "3")
    private String schoolNo;

    @NotEmpty
    @Size(max = 64)
    @OpenApiField(desc = "用户编码", constraint = "用户编码", demo = "8888")
    private String userNo;
}

package com.qiudot.edu.openapi.message;

import com.acooly.openapi.framework.common.annotation.OpenApiField;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

/**
 * @author qiuboboy@qq.com
 * @date 2018-10-19 15:09
 */
@Slf4j
@Getter
@Setter
public class BindingCardReq extends CampusReq {
    @NotEmpty
    @Size(max = 20)
    @OpenApiField(desc = "绑卡用户支付宝唯一Id", constraint = "绑卡用户支付宝唯一Id", demo = "2088022138611853")
    private String alipayUserId;
    @NotEmpty
    @Size(max = 18)
    @OpenApiField(desc = "绑卡用户身份证号", constraint = "绑卡用户身份证号", demo = "110101199003077651")
    private String certNo;
}

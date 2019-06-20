package com.qiudot.edu.openapi.message;

import com.acooly.core.utils.Money;
import com.acooly.core.utils.validate.jsr303.MoneyConstraint;
import com.acooly.openapi.framework.common.annotation.OpenApiField;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author zhike 2018/8/1 21:54
 */
@Getter
@Setter
public class DeductPayRequest extends CampusReq {

    /**
     * 用户标识信息
     * <p>
     * 交易订单号(字母或数字)
     */
    @NotEmpty
    @Size(max = 64)
    @OpenApiField(desc = "交易订单号", constraint = "交易订单号", demo = "121928392138321")
    private String merchOrderNo;
    /**
     * 充值金额
     */
    @MoneyConstraint(min = 1)
    @NotNull
    @OpenApiField(desc = "消费金额", constraint = "消费金额", demo = "6.66")
    private Money amount;

    /**
     * 场景说明
     */
    @NotEmpty
    @Size(max = 256)
    @OpenApiField(desc = "场景说明", constraint = "场景说明", demo = "午餐")
    private String subject;


    /**
     * 摘要
     */
    @Size(max = 64)
    @OpenApiField(desc = "备注", constraint = "备注", demo = "消费扣款")
    private String memo;
}

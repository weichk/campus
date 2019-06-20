package com.qiudot.edu.service.base.dto;

import com.acooly.core.utils.Money;
import com.acooly.core.utils.validate.jsr303.MoneyConstraint;
import com.qiudot.edu.common.base.BaseDto;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author zhike@acooly.com
 * @date 2018-10-09 12:25
 */
@Getter
@Setter
public class GatewayDeductPayDto extends BaseDto {

    /**
     * 代扣金额
     */
    @MoneyConstraint(min = 1)
    @NotNull
    private Money amount;

    /**
     * 签约协议号
     */
    @NotBlank
    private String agreementNo;

    /**
     * 学校协议号
     */
    private String schoolAnthCode;

    /**
     * 场景说明
     */
    @NotBlank
    private String subject;

    /**
     * 用户IP
     */
    private String userIp;
}

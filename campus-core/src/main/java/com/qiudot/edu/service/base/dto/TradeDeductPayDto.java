package com.qiudot.edu.service.base.dto;

import com.acooly.core.utils.Money;
import com.acooly.core.utils.validate.jsr303.MoneyConstraint;
import com.qiudot.edu.common.base.BaseDto;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author zhike@acooly.com
 * @date 2018-10-09 12:25
 */
@Getter
@Setter
public class TradeDeductPayDto extends BaseDto {

    /**
     * 一卡通卡号
     */
    @NotBlank
    private String cardNo;

    /**
     * 学校编码
     */
    @NotBlank
    private String schoolNo;

    /**
     * 代扣金额
     */
    @NotNull
    @MoneyConstraint(min = 1)
    private Money amount;

    /**
     * 场景说明
     */
    @NotEmpty
    @Size(max = 256)
    private String subject;


    /**
     * 摘要
     */
    @Size(max = 64)
    private String memo;
}

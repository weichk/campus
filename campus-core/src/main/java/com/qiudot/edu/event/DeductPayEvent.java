package com.qiudot.edu.event;

import com.acooly.core.utils.Money;
import com.acooly.core.utils.validate.jsr303.MoneyConstraint;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Auther: zhike
 * @Date: 2018/8/31 16:10
 * @Description:
 */
@Getter
@Setter
@ToString
public class DeductPayEvent implements Serializable {

    /**
     * 商户订单号
     */
    private String partnerId;
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
     * 业务订单号
     */
    @NotBlank
    private String bizOrderNo;

    /**
     * 用户ID
     */
    private String userNo;

    /**
     * 学校授权码
     */
    private String schoolAnthCode;

    /**
     * 场景说明
     */
    @NotBlank
    private String subject;

    /**
     * 用户请求IP
     */
    private String userIp;


    public DeductPayEvent() {
    }

    public DeductPayEvent(String partnerId,Money amount, String schoolAnthCode, String agreementNo, String subject, String bizOrderNo, String userNo) {
        this.partnerId = partnerId;
        this.amount = amount;
        this.schoolAnthCode = schoolAnthCode;
        this.agreementNo = agreementNo;
        this.subject = subject;
        this.bizOrderNo = bizOrderNo;
        this.userNo = userNo;
    }
}

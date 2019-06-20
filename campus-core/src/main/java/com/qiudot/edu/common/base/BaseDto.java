package com.qiudot.edu.common.base;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @author zhike@acooly.com
 * @date 2018-10-09 14:29
 */
@Getter
@Setter
public class BaseDto implements Serializable {

    /**
     * 商户号
     */
    @NotBlank
    private String partnerId;

    /**
     * 业务订单号
     */
    @NotBlank
    private String bizOrderNo;

    /**
     * 商户订单号
     */
    private String merchOrderNo;

    /**
     * 用户ID
     */
    private String userNo;
}

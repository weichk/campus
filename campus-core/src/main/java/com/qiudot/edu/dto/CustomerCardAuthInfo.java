/*
 * www.prosysoft.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * shuijing 2018-10-10 15:35 创建
 */
package com.qiudot.edu.dto;

import lombok.Data;

/**
 * 会员卡授权信息
 *
 * @author shuijing
 */
@Data
public class CustomerCardAuthInfo {

    /**
     * 用户签约号
     */
    private String agreementNo;

    /**
     * 学校签约权码
     */
    private String aliAuthCode;

    /**
     * 用户No
     */
    private String userNo;
}

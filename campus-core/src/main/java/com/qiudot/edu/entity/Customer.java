/*
* qiudot.com Inc.
* Copyright (c) 2018 All Rights Reserved.
* create by qiudot
* date:2018-10-08
*/
package com.qiudot.edu.entity;


import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.core.common.type.DBMap;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 会员信息 Entity
 *
 * @author qiudot
 * Date: 2018-10-08 14:25:29
 */
@Entity
@Table(name = "campus_customer")
@Getter
@Setter
public class Customer extends AbstractEntity {

	/** 用户号 */
	@Size(max=64)
    private String userNo;

	/** 用户名 */
	@Size(max=64)
    private String userName;

	/** 姓名 */
	@Size(max=32)
    private String realName;

	/** 身份证号 */
	@Size(max=32)
    private String certNo;

	/** 状态 */
	@Size(max=32)
    private String customerStatus;

	/** 阿里支付授权码 */
	@Size(max=128)
    private String aliAuthCode;

	/** 手机号 */
	@Size(max=11)
    private String mobileNo;

	/** json扩展信息 */
	@Size(max=2048)
    private DBMap extJson;

	/**
	 * 阿里用户id
	 */
	@Size(max=64)
	private String alipayUserId;

	/**
	 * 阿里用户appId
	 */
	@Size(max=64)
	private String authAppId;

	/**
	 * 用户签约号
	 */
	@Size(max=64)
	private String agreementNo;

	/**
	 * 用户签约时间
	 */
	private Date signTime;

}

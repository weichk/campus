/*
* qiudot.com Inc.
* Copyright (c) 2018 All Rights Reserved.
* create by qiudot
* date:2018-10-08
*/
package com.qiudot.edu.entity;


import com.acooly.core.common.domain.AbstractEntity;
import com.qiudot.edu.enums.ShoolStatusEnum;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * 学校信息 Entity
 *
 * @author qiudot
 * Date: 2018-10-08 14:25:30
 */
@Entity
@Table(name = "campus_shool")
@Getter
@Setter
public class Shool extends AbstractEntity {

	/** 学校编码 */
	@Size(max=32)
    private String shoolCode;

	/** 学校名称 */
	@Size(max=32)
    private String shoolName;

	/** 学校联系人姓名 */
	@Size(max=32)
    private String contactsName;

	/** 学校联系电话 */
	@Size(max=16)
    private String mobileNo;

	/** 支付宝操作权码 */
	@Size(max=128)
    private String aliAuthCode;

	/** 学校支付宝PID */
	@NotEmpty
	@Size(max=64)
	private String aliPid;

	/** 学校地址 */
	@Size(max=128)
    private String address;

	/** 省 */
	@Size(max=20)
    private String province;

	/** 市 */
	@Size(max=20)
    private String city;

	/** 县 */
	@Size(max=20)
    private String district;

	/** 状态 */
    @Enumerated(EnumType.STRING)
    private ShoolStatusEnum shoolStatus;

	/** json扩展信息 */
	@Size(max=512)
    private String extJson;

//	/**商户授权令牌*/
//	@Size(max=64)
//	private String appAuthToken;



}

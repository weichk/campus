/*
* qiudot.com Inc.
* Copyright (c) 2018 All Rights Reserved.
* create by qiudot
* date:2018-10-08
*/
package com.qiudot.edu.entity;


import com.acooly.core.common.domain.AbstractEntity;
import com.qiudot.edu.enums.CardStatusEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * 会员卡信息 Entity
 *
 * @author qiudot
 * Date: 2018-10-08 14:25:29
 */
@Entity
@Table(name = "campus_customer_card")
@Getter
@Setter
public class CustomerCard extends AbstractEntity {

	/** 持卡编号 */
	@Size(max=32)
    private String cardNo;

	/** 学校编码 */
	@Size(max=32)
    private String shoolCode;

	/** 学校名称 */
	@Size(max=32)
    private String shoolName;

	/** 会员用户编号 */
	@Size(max=64)
    private String customerUserNo;

	/** 姓名 */
	@Size(max=32)
    private String realName;

	/** 身份证号 */
	@Size(max=32)
    private String certNo;

	/** 状态 */
    @Enumerated(EnumType.STRING)
    private CardStatusEnum cardStatus;

	/** 手机号 */
	@Size(max=11)
    private String mobileNo;

	/** json扩展信息 */
	@Size(max=512)
    private String extJson;

	/**
	 * 阿里用户id
	 */
	@Size(max=64)
	private String alipayUserId;
}

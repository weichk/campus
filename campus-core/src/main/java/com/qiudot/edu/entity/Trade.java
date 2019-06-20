/*
* qiudot.com Inc.
* Copyright (c) 2018 All Rights Reserved.
* create by qiudot
* date:2018-10-08
*/
package com.qiudot.edu.entity;


import com.acooly.core.common.domain.AbstractEntity;
import com.qiudot.edu.enums.GatewayStautsEnum;
import com.qiudot.edu.enums.TradeStatusEnum;
import com.qiudot.edu.enums.TradeTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 交易记录 Entity
 *
 * @author qiudot
 * Date: 2018-10-08 14:25:30
 */
@Entity
@Table(name = "campus_trade")
@Getter
@Setter
public class Trade extends AbstractEntity {

	/** 商户号 */
	@Size(max=64)
	private String partnerId;

	/** 交易订单号 */
	@Size(max=64)
    private String bizOrderNo;

	@Size(max=64)
	private String merchOrderNo;

	/** 学校编号 */
	@Size(max=64)
    private String schoolNo;

	/** 会员ID */
	@Size(max=64)
    private String customerUserNo;

	/** 持卡编号 */
	@Size(max=64)
    private String cardNo;

	/**
	 * 签约协议号
	 */
	@Size(max=64)
	private String agreementNo;

	/**
	 * 学校授权编码
	 */
	@Size(max=128)
	private String schoolAnthCode;


	/** 交易类型 */
    @Enumerated(EnumType.STRING)
    private TradeTypeEnum tradeType;

	/** 交易金额 */
    private Long amount;

	/** 交易后余额 */
    private Long balance;

	/** 交易状态 */
    @Enumerated(EnumType.STRING)
    private TradeStatusEnum tradeStatus;

	/** 网关状态 */
    @Enumerated(EnumType.STRING)
    private GatewayStautsEnum gatewayStauts;

	/** 外部备注 */
	@Size(max=128)
    private String comment;

	/** 内部备注 */
	@Size(max=128)
    private String memo;

	/** json扩展信息 */
	@Size(max=512)
    private String extJson;

	/** 交易完成时间 */
    private Date tradeTime;

	/**
	 * 用户IP
	 */
	private String userIp;

}

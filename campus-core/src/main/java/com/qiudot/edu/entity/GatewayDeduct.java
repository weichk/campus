/*
* qiudot.com Inc.
* Copyright (c) 2018 All Rights Reserved.
* create by qiudot
* date:2018-10-08
*/
package com.qiudot.edu.entity;


import com.acooly.core.common.domain.AbstractEntity;
import com.qiudot.edu.enums.ChannelTypeEnum;
import com.qiudot.edu.enums.NotifyStatusEnum;
import com.qiudot.edu.enums.StatusEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 网关代扣记录 Entity
 *
 * @author qiudot
 * Date: 2018-10-08 14:25:30
 */
@Entity
@Table(name = "campus_gateway_deduct")
@Getter
@Setter
public class GatewayDeduct extends AbstractEntity {

	/** 商户号 */
	@Size(max=64)
	private String partnerId;

	/** 渠道类型 */
    @Enumerated(EnumType.STRING)
    private ChannelTypeEnum channelType;

	/** 渠道服务api */
	@Size(max=32)
    private String channelApi;

	/** 批次号 */
	@Size(max=64)
    private String batchNo;

	/** 业务订单号 */
	@Size(max=64)
    private String bizOrderNo;

	/** 银行订单号 */
	@Size(max=64)
    private String bankOrderNo;

	/** 交易主体用户号 */
	@Size(max=64)
    private String userNo;

	/** 用户ip */
	@Size(max=32)
    private String userIp;

	/** 交易对象用户号 */
	@Size(max=64)
    private String refUserNo;

	/** 订单状态 */
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

	/** 通知状态 */
    @Enumerated(EnumType.STRING)
    private NotifyStatusEnum notifyStatus;

	/** 通知次数 */
    private Integer notifySendCount;

	/** 通知时间 */
    private Date notifyDate;

	/** 交易申请金额 */
    private Long amount;

	/** 成功金额 */
    private Long sucAmount;

	/** 内部备注 */
	@Size(max=128)
    private String memo;

	/** 有效时间 */
    private Date validDate;

	/** json扩展信息 */
	@Size(max=512)
    private String extJson;

	/** 银行响应报文 */
	@Size(max=0)
    private String bankRespMessage;

}

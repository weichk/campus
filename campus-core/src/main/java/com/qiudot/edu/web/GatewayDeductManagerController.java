/*
* qiudot.com Inc.
* Copyright (c) 2018 All Rights Reserved.
* create by qiudot
* date:2018-10-08
*/
package com.qiudot.edu.web;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.qiudot.edu.entity.GatewayDeduct;
import com.qiudot.edu.enums.ChannelTypeEnum;
import com.qiudot.edu.enums.NotifyStatusEnum;
import com.qiudot.edu.enums.StatusEnum;
import com.qiudot.edu.service.GatewayDeductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 网关代扣记录 管理控制器
 * 
 * @author qiudot
 * Date: 2018-10-08 14:25:30
 */
@Controller
@RequestMapping(value = "/manage/demo/gatewayDeduct")
public class GatewayDeductManagerController extends AbstractJQueryEntityController<GatewayDeduct, GatewayDeductService> {
	

	{
		allowMapping = "*";
	}

	@SuppressWarnings("unused")
	@Autowired
	private GatewayDeductService gatewayDeductService;

	
	@Override
	protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
		model.put("allChannelTypes", ChannelTypeEnum.mapping());
		model.put("allStatuss", StatusEnum.mapping());
		model.put("allNotifyStatuss", NotifyStatusEnum.mapping());
	}

}

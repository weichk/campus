/*
* qiudot.com Inc.
* Copyright (c) 2018 All Rights Reserved.
* create by qiudot
* date:2018-10-08
*/
package com.qiudot.edu.web;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.qiudot.edu.entity.Shool;
import com.qiudot.edu.enums.ShoolStatusEnum;
import com.qiudot.edu.service.ShoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 学校信息 管理控制器
 * 
 * @author qiudot
 * Date: 2018-10-08 14:25:30
 */
@Controller
@RequestMapping(value = "/manage/demo/shool")
public class ShoolManagerController extends AbstractJQueryEntityController<Shool, ShoolService> {
	

	{
		allowMapping = "*";
	}

	@SuppressWarnings("unused")
	@Autowired
	private ShoolService shoolService;

	
	@Override
	protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
		model.put("allShoolStatuss", ShoolStatusEnum.mapping());
		model.put("authUrl", shoolService.getAuthUrl());

	}

}

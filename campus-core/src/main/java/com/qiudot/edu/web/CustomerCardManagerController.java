/*
* qiudot.com Inc.
* Copyright (c) 2018 All Rights Reserved.
* create by qiudot
* date:2018-10-08
*/
package com.qiudot.edu.web;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.qiudot.edu.entity.CustomerCard;
import com.qiudot.edu.enums.CardStatusEnum;
import com.qiudot.edu.service.CustomerCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 会员卡信息 管理控制器
 * 
 * @author qiudot
 * Date: 2018-10-08 14:25:29
 */
@Controller
@RequestMapping(value = "/manage/demo/customerCard")
public class CustomerCardManagerController extends AbstractJQueryEntityController<CustomerCard, CustomerCardService> {
	

	{
		allowMapping = "*";
	}

	@SuppressWarnings("unused")
	@Autowired
	private CustomerCardService customerCardService;

	
	@Override
	protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
		model.put("allCardStatuss", CardStatusEnum.mapping());
	}

}

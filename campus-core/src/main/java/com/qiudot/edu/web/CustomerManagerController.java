/*
* qiudot.com Inc.
* Copyright (c) 2018 All Rights Reserved.
* create by qiudot
* date:2018-10-08
*/
package com.qiudot.edu.web;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.qiudot.edu.entity.Customer;
import com.qiudot.edu.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 会员信息 管理控制器
 * 
 * @author qiudot
 * Date: 2018-10-08 14:25:29
 */
@Controller
@RequestMapping(value = "/manage/demo/customer")
public class CustomerManagerController extends AbstractJQueryEntityController<Customer, CustomerService> {
	

	{
		allowMapping = "*";
	}

	@SuppressWarnings("unused")
	@Autowired
	private CustomerService customerService;

	

}

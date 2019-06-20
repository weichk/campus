/*
 * qiudot.com Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by qiudot
 * date:2018-10-08
 *
 */
package com.qiudot.edu.service;

import com.acooly.core.common.service.EntityService;
import com.qiudot.edu.entity.Customer;

/**
 * 会员信息 Service接口
 * <p>
 * Date: 2018-10-08 14:25:29
 *
 * @author qiudot
 */
public interface CustomerService extends EntityService<Customer> {

    Customer findByAlipayUserId(String alipayUserId);
}

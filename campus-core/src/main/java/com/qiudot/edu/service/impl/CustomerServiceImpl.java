/*
 * qiudot.com Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by qiudot
 * date:2018-10-08
 */
package com.qiudot.edu.service.impl;

import com.acooly.core.common.service.EntityServiceImpl;
import com.qiudot.edu.dao.CustomerDao;
import com.qiudot.edu.entity.Customer;
import com.qiudot.edu.service.CustomerService;
import org.springframework.stereotype.Service;

/**
 * 会员信息 Service实现
 * <p>
 * Date: 2018-10-08 14:25:29
 *
 * @author qiudot
 */
@Service("customerService")
public class CustomerServiceImpl extends EntityServiceImpl<Customer, CustomerDao> implements CustomerService {

    @Override
    public Customer findByAlipayUserId(String alipayUserId) {
        return getEntityDao().findByAlipayUserId(alipayUserId);
    }
}

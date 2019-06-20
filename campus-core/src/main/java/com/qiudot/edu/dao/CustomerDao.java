/*
 * qiudot.com Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by qiudot
 * date:2018-10-08
 */
package com.qiudot.edu.dao;

import com.acooly.module.mybatis.EntityMybatisDao;
import com.qiudot.edu.entity.Customer;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 会员信息 Mybatis Dao
 * <p>
 * Date: 2018-10-08 14:25:29
 *
 * @author qiudot
 */
public interface CustomerDao extends EntityMybatisDao<Customer> {

    @Select("select * from campus_customer where alipay_user_id=#{alipayUserId}")
    Customer findByAlipayUserId(@Param("alipayUserId") String alipayUserId);

}

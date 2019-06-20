/*
 * qiudot.com Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by qiudot
 * date:2018-10-08
 */
package com.qiudot.edu.dao;

import com.acooly.module.mybatis.EntityMybatisDao;
import com.qiudot.edu.entity.GatewayDeduct;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 网关代扣记录 Mybatis Dao
 * <p>
 * Date: 2018-10-08 14:25:30
 *
 * @author qiudot
 */
public interface GatewayDeductDao extends EntityMybatisDao<GatewayDeduct> {

    /**
     * 根据订单号查询交易订单
     *
     * @param bizOrderNo
     * @return
     */
    @Select("SELECT * FROM campus_gateway_deduct WHERE biz_order_no = #{bizOrderNo}")
    GatewayDeduct getEntityByBizOrderNo(@Param("bizOrderNo") String bizOrderNo);

}

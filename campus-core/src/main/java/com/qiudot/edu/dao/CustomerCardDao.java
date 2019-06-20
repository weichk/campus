/*
 * qiudot.com Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by qiudot
 * date:2018-10-08
 */
package com.qiudot.edu.dao;

import com.acooly.module.mybatis.EntityMybatisDao;
import com.qiudot.edu.entity.CustomerCard;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 会员卡信息 Mybatis Dao
 * <p>
 * Date: 2018-10-08 14:25:29
 *
 * @author qiudot
 */
public interface CustomerCardDao extends EntityMybatisDao<CustomerCard> {

    @Select("select * from campus_customer_card where card_no=#{cardNo}")
    CustomerCard findByCardNo(@Param("cardNo") String cardNo);


    @Select("select * from campus_customer_card where card_no = #{cardNo} and shool_code = #{schoolCode}")
    CustomerCard findByCardNoAndSchoolCode(@Param("cardNo") String cardNo, @Param("schoolCode") String schoolCode);


    @Select("select * from campus_customer_card where card_no = #{cardNo} and shool_code = #{schoolCode} and alipay_user_id = #{aliPayUserId}")
    CustomerCard findByCardNoAndSchoolCodeAndAlipayId(@Param("cardNo") String cardNo, @Param("schoolCode") String schoolCode, @Param("aliPayUserId") String aliPayUserId);

    /**
     * 获取当前用户最新帮的一张卡
     *
     * @param userNo
     * @return
     */
    @Select("SELECT * FROM campus_customer_card WHERE customer_user_no = #{userNo} ORDER BY id DESC LIMIT 1")
    CustomerCard getLastBindCardByUserNo(@Param("userNo") String userNo);

    /**
     * 获取当前用户最新帮的一张卡
     *
     * @param userNo
     * @return
     */
    @Select("SELECT * FROM campus_customer_card WHERE customer_user_no = #{userNo} ORDER BY id DESC")
    List<CustomerCard> getCardListByUserNo(@Param("userNo") String userNo);
}

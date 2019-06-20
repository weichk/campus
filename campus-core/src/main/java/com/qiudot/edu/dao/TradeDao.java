/*
 * qiudot.com Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by qiudot
 * date:2018-10-08
 */
package com.qiudot.edu.dao;

import com.acooly.module.mybatis.EntityMybatisDao;
import com.qiudot.edu.entity.Trade;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 交易记录 Mybatis Dao
 * <p>
 * Date: 2018-10-08 14:25:30
 *
 * @author qiudot
 */
public interface TradeDao extends EntityMybatisDao<Trade> {

    /**
     * 查询当前卡号最后一笔交易
     * @param cardNo
     * @return
     */
    @Select("SELECT * FROM campus_trade WHERE card_no = #{cardNo} and school_no = #{schoolNo} and merch_order_no <> #{merchOrderNo} and partner_id = #{partnerId} ORDER BY id DESC limit 1")
    Trade getLastEntityByCardNoAndSchoolNo(@Param("cardNo") String cardNo,@Param("schoolNo") String schoolNo,@Param("merchOrderNo") String merchOrderNo,@Param("partnerId") String partnerId);

    /**
     * 根据订单号查询交易订单
     * @param bizOrderNo
     * @return
     */
    @Select("SELECT * FROM campus_trade WHERE biz_order_no = #{bizOrderNo}")
    Trade getEntityByBizOrderNo(@Param("bizOrderNo") String bizOrderNo);

    /**
     * 根据商户订单号查询交易订单
     * @param merchOrderNo
     * @return
     */
    @Select("SELECT * FROM campus_trade WHERE merch_order_no = #{merchOrderNo} and partner_id = #{partnerId}")
    Trade getEntityByMerchOrderNo(@Param("merchOrderNo") String merchOrderNo,@Param("partnerId") String partnerId);

    /**
     * 查询需要做代扣的订单列表
     * @return
     */
    @Select("SELECT * FROM campus_trade WHERE trade_status <> 'SUCCESS' and gateway_stauts not in ('PROCESSING','SUCCESS')")
    List<Trade> queryNeedDeductPayDatas();

    /**
     * 查询需要做代扣查询的订单列表
     * @return
     */
    @Select("SELECT * FROM campus_trade WHERE trade_status = 'PROCESSING' and gateway_stauts = 'PROCESSING' AND DATE_SUB(CURDATE(), INTERVAL 3 DAY) <= create_time")
    List<Trade> queryNeedDeductPayQueryDatas();
}

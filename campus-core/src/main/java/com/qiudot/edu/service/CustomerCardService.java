/*
 * qiudot.com Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by qiudot
 * date:2018-10-08
 *
 */
package com.qiudot.edu.service;

import com.acooly.core.common.service.EntityService;
import com.qiudot.edu.dto.CustomerCardAuthInfo;
import com.qiudot.edu.entity.CustomerCard;

import java.util.List;

/**
 * 会员卡信息 Service接口
 * <p>
 * Date: 2018-10-08 14:25:29
 *
 * @author qiudot
 */
public interface CustomerCardService extends EntityService<CustomerCard> {

    /**
     * 根据一卡通卡号查询授权信息
     *
     * @param cardNo
     * @return
     */
    CustomerCardAuthInfo getCardAuthInfoByCardNo(String cardNo, String schoolNo);

    /**
     * 获取当前用户最新帮的一张卡
     *
     * @param userNo
     * @return
     */
    CustomerCard getLastBindCardByUserNo(String userNo);

    /**
     * 根据卡号和学校编号查询卡信息
     *
     * @param cardNo
     * @return
     */
    CustomerCard findByCardNoAndSchoolCode(String cardNo, String schoolCode);


    CustomerCard findByCardNoAndSchoolCodeAndAlipayId(String cardNo, String schoolCode,String aliPayUserId);


    /**
     * 获取用户所有卡列表
     * @param userNo
     * @return
     */
    List<CustomerCard> getCardListByUserNo(String userNo);
}

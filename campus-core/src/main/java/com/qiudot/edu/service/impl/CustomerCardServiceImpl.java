/*
 * qiudot.com Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by qiudot
 * date:2018-10-08
 */
package com.qiudot.edu.service.impl;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.service.EntityServiceImpl;
import com.qiudot.edu.dao.CustomerCardDao;
import com.qiudot.edu.dto.CustomerCardAuthInfo;
import com.qiudot.edu.entity.Customer;
import com.qiudot.edu.entity.CustomerCard;
import com.qiudot.edu.entity.Shool;
import com.qiudot.edu.service.CustomerCardService;
import com.qiudot.edu.service.CustomerService;
import com.qiudot.edu.service.ShoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 会员卡信息 Service实现
 * <p>
 * Date: 2018-10-08 14:25:29
 *
 * @author qiudot
 */
@Service("customerCardService")
public class CustomerCardServiceImpl extends EntityServiceImpl<CustomerCard, CustomerCardDao> implements CustomerCardService {

    @Autowired
    private ShoolService shoolService;
    @Autowired
    private CustomerService customerService;

    @Override
    public CustomerCardAuthInfo getCardAuthInfoByCardNo(String cardNo, String schoolNo) {
        CustomerCard customerCard = getEntityDao().findByCardNo(cardNo);
        if (customerCard == null) {
            throw new BusinessException("还未绑卡，请绑卡后查询");
        }
        //学校编码
        Shool shool = shoolService.findBySchoolCode(schoolNo);
        if (shool == null) {
            throw new BusinessException("绑卡学校不存在");
        }

        //阿里用户id
        String alipayUserId = customerCard.getAlipayUserId();
        Customer customer = customerService.findByAlipayUserId(alipayUserId);
        if (customer == null) {
            throw new BusinessException("用户还未支付宝签约");
        }

        CustomerCardAuthInfo customerCardAuthInfo = new CustomerCardAuthInfo();
        customerCardAuthInfo.setAgreementNo(customer.getAgreementNo());
        customerCardAuthInfo.setAliAuthCode(shool.getAliAuthCode());
        customerCardAuthInfo.setUserNo(customer.getUserNo());

        return customerCardAuthInfo;
    }

    @Override
    public CustomerCard getLastBindCardByUserNo(String userNo) {
        return this.getEntityDao().getLastBindCardByUserNo(userNo);
    }

    @Override
    public CustomerCard findByCardNoAndSchoolCode(String cardNo, String schoolCode) {
        return this.getEntityDao().findByCardNoAndSchoolCode(cardNo, schoolCode);
    }

    @Override
    public CustomerCard findByCardNoAndSchoolCodeAndAlipayId(String cardNo, String schoolCode, String aliPayUserId) {
        return this.getEntityDao().findByCardNoAndSchoolCodeAndAlipayId(cardNo,schoolCode,aliPayUserId);
    }

    @Override
    public List<CustomerCard> getCardListByUserNo(String userNo) {
        return this.getEntityDao().getCardListByUserNo(userNo);
    }
}

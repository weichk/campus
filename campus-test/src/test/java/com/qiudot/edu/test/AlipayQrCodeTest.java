/*
 * www.prosysoft.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * shuijing 2018-10-09 16:55 创建
 */
package com.qiudot.edu.test;

import com.acooly.module.test.AppWebTestBase;
import com.qiudot.edu.dto.CustomerCardAuthInfo;
import com.qiudot.edu.service.CustomerCardService;
import com.qiudot.edu.service.ShoolService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.acooly.core.common.boot.Apps.SPRING_PROFILE_ACTIVE;

/**
 * @author shuijing
 */
public class AlipayQrCodeTest extends AppWebTestBase {

    static {
        System.setProperty(SPRING_PROFILE_ACTIVE, "dev");
    }

    @Autowired
    private ShoolService shoolService;
    @Autowired
    private CustomerCardService customerCardService;

    @Test
    public void testGetgetAuthUrlByID() throws Exception {
        String appid="2016092200570385";
        String authUrl = shoolService.getAuthUrl();
        System.out.println(authUrl);
    }

    @Test
    public void testGetgetAuthUrlQrCode() throws Exception {
        String appid="2016092200570385";
        String oauth2Image = shoolService.getOauth2Image();
        System.out.println(oauth2Image);
    }

    @Test
    public void testgetCardAuthInfoByCardNo() throws Exception {
        CustomerCardAuthInfo cardAuthInfoByCardNo = customerCardService.getCardAuthInfoByCardNo("8888","3");
        System.out.println(cardAuthInfoByCardNo);
    }
}

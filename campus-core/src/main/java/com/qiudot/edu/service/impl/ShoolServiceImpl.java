/*
 * qiudot.com Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by qiudot
 * date:2018-10-08
 */
package com.qiudot.edu.service.impl;

import com.acooly.core.common.boot.Env;
import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.core.utils.Exceptions;
import com.acooly.module.ofile.OFileProperties;
import com.qiudot.edu.config.CampusProperties;
import com.qiudot.edu.dao.ShoolDao;
import com.qiudot.edu.entity.Shool;
import com.qiudot.edu.service.ShoolService;
import com.qiudot.edu.util.QRCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 学校信息 Service实现
 * <p>
 * Date: 2018-10-08 14:25:30
 *
 * @author qiudot
 */
@Slf4j
@Service("shoolService")
public class ShoolServiceImpl extends EntityServiceImpl<Shool, ShoolDao> implements ShoolService {

    @Autowired
    private CampusProperties campusProperties;
    @Autowired
    private OFileProperties oFileProperties;

    @Override
    public Shool findBySchoolCode(String code) {
        return this.getEntityDao().findBySchoolCode(code);
    }

    @Override
    public Shool findByPid(String pid) {
        return this.getEntityDao().findByPid(pid);
    }

    @Override
    public String getOauth2Image() {
        String storageRoot = oFileProperties.getStorageRoot();
        File dir = new File(storageRoot + "authImg");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dir.getPath() + File.separator + System.getProperty(Env.ENV_KEY) + "-ivsAuth.png");

        if (!file.exists()) {
            try {
                int margin = 0;
                String level = "L";
                String format = "jpg";
                String content = getAuthUrl();
                QRCodeUtil.createQRCode(new FileOutputStream(file), 200, 200, margin, level, format, content);
            } catch (Exception e) {
                throw Exceptions.rethrowBusinessException(e);
            }
        }
        log.info("授权图片路径：{}", file.getPath());

        String path = file.getPath();
        String serverRoot = oFileProperties.getServerRoot();
        String visitPath = serverRoot + path.substring(path.indexOf(storageRoot) + storageRoot.length() - 1);
        return visitPath;
    }

    @Override
    public String getAuthUrl() {
        String authRedirectUri;
        try {
            authRedirectUri = URLEncoder.encode(campusProperties.getSchoolAgreementSignNotifyUrl(), Charsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
            throw Exceptions.rethrowBusinessException(e);
        }
        return campusProperties.getAlipayAuthUrlPrefix() + "?app_id=" + campusProperties.getAppId() + "&redirect_uri=" + authRedirectUri;
    }
}

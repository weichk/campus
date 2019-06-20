/*
 * qiudot.com Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by qiudot
 * date:2018-10-08
 *
 */
package com.qiudot.edu.service;

import com.acooly.core.common.service.EntityService;
import com.qiudot.edu.entity.Shool;
import org.apache.ibatis.annotations.Param;

/**
 * 学校信息 Service接口
 * <p>
 * Date: 2018-10-08 14:25:30
 *
 * @author qiudot
 */
public interface ShoolService extends EntityService<Shool> {

    Shool findBySchoolCode(@Param("schoolCode") String schoolCode);

    /**
     * 根据学校支付宝PID查询学校
     */
    Shool findByPid(@Param("pid") String pid);

    String getOauth2Image();

    String getAuthUrl();
}

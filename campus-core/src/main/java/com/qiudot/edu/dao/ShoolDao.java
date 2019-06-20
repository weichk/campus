/*
 * qiudot.com Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by qiudot
 * date:2018-10-08
 */
package com.qiudot.edu.dao;

import com.acooly.module.mybatis.EntityMybatisDao;
import com.qiudot.edu.entity.Shool;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 学校信息 Mybatis Dao
 * <p>
 * Date: 2018-10-08 14:25:30
 *
 * @author qiudot
 */
public interface ShoolDao extends EntityMybatisDao<Shool> {

    @Select("select * from campus_shool where shool_code=#{schoolCode}")
    Shool findBySchoolCode(@Param("schoolCode") String schoolCode);

    @Select("select * from campus_shool where ali_pid=#{pid}")
    Shool findByPid(@Param("pid") String pid);
}

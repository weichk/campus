/*
 *
 * www.qiudot.com Inc.
 * Copyright (c) 2018  All Rights Reserved
 */

package com.qiudot.edu.test;

import com.acooly.core.utils.Ids;
import com.acooly.openapi.framework.common.message.ApiRequest;
import com.acooly.openapi.framework.core.test.AbstractApiServieTests;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by liubin@qiudot.com on 2018-08-06 15:21.
 */
public class AbstractDassetsApiTestBase extends AbstractApiServieTests {

    {

        gatewayUrl = "http://47.100.217.13:8888/gateway.do";
        partnerId = "qiuduo";
        secretKey = "876e3faa7830683075e4f7bb2f3e0176";
        accessKey = "18101113494073100001";
    }

    @Override
    protected <T> T request(ApiRequest request, Class<T> clazz) {
        request.setRequestNo(Ids.getDid());
        request.setPartnerId(partnerId);
        return super.request(request, clazz);
    }


    protected void simpleFillEntity(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        for (Field field : fields) {
            if ((field.getModifiers() & Modifier.STATIC) == Modifier.STATIC) {
                continue;
            }
            String methodName = "set" + StringUtils.capitalize(field.getName());
            try {
                Method method = o.getClass().getMethod(methodName, field.getType());
                Random r = new Random();
                int value = r.nextInt(100);
                if ("String".equals(field.getType().getSimpleName())) {
                    method.invoke(o, String.valueOf(value / 10));
                } else if ("Long".equals(field.getType().getSimpleName()) && !("id".equals(field.getName()))) {
                    method.invoke(o, Long.valueOf(value));
                } else if ("Integer".equals(field.getType().getSimpleName()) && !("id".equals(field.getName()))) {
                    method.invoke(o, Integer.valueOf(value));
                } else if ("Calendar".equals(field.getType().getSimpleName())) {
                    method.invoke(o, Calendar.getInstance());
                } else if ("Date".equals(field.getType().getSimpleName())) {
                    method.invoke(o, Calendar.getInstance().getTime());
                } else if ("Float".equals(field.getType().getSimpleName())) {
                    method.invoke(o, Float.valueOf(value));
                } else if (field.getType().isEnum()) {
                    method.invoke(o, field.getType().getEnumConstants()[0]);
                }
            } catch (SecurityException e) {
                logger.warn("SecurityException: " + methodName + " method");
            } catch (NoSuchMethodException e) {
                logger.warn("NoSuchMethodException: " + methodName + " method");
            } catch (IllegalArgumentException e) {
                logger.warn("IllegalArgumentException: invoke method " + methodName + " ");
            } catch (IllegalAccessException e) {
                logger.warn("IllegalAccessException: invoke method " + methodName + " ");
            } catch (InvocationTargetException e) {
                logger.warn("InvocationTargetException: invoke method " + methodName + " ");
            }
        }
    }
}

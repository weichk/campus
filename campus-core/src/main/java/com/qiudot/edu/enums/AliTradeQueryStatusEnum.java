/*
 * qiudot.com Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by qiudot
 * date:2018-10-08
 *
 */
package com.qiudot.edu.enums;

import com.acooly.core.utils.enums.Messageable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员卡信息 CardStatusEnum 枚举定义
 *
 * @author qiudot
 * Date: 2018-10-08 14:25:29
 */
public enum AliTradeQueryStatusEnum implements Messageable {

    WAIT_BUYER_PAY("WAIT_BUYER_PAY", "交易创建，等待买家付款"),

    TRADE_CLOSED("TRADE_CLOSED", "未付款交易超时关闭，或支付完成后全额退款"),

    TRADE_SUCCESS("TRADE_SUCCESS", "交易支付成功"),

    TRADE_FINISHED("TRADE_FINISHED", "交易结束，不可退款"),
    ;

    private final String code;
    private final String message;

    private AliTradeQueryStatusEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }

    public static Map<String, String> mapping() {
        Map<String, String> map = new LinkedHashMap<String, String>();
        for (AliTradeQueryStatusEnum type : values()) {
            map.put(type.getCode(), type.getMessage());
        }
        return map;
    }

    /**
     * 通过枚举值码查找枚举值。
     *
     * @param code 查找枚举值的枚举值码。
     * @return 枚举值码对应的枚举值。
     * @throws IllegalArgumentException 如果 code 没有对应的 Status 。
     */
    public static AliTradeQueryStatusEnum find(String code) {
        for (AliTradeQueryStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

    /**
     * 获取全部枚举值。
     *
     * @return 全部枚举值。
     */
    public static List<AliTradeQueryStatusEnum> getAll() {
        List<AliTradeQueryStatusEnum> list = new ArrayList<AliTradeQueryStatusEnum>();
        for (AliTradeQueryStatusEnum status : values()) {
            list.add(status);
        }
        return list;
    }

    /**
     * 获取全部枚举值码。
     *
     * @return 全部枚举值码。
     */
    public static List<String> getAllCode() {
        List<String> list = new ArrayList<String>();
        for (AliTradeQueryStatusEnum status : values()) {
            list.add(status.code());
        }
        return list;
    }

}

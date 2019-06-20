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
 * 网关代扣记录 NotifyStatusEnum 枚举定义
 * 
 * @author qiudot
 * Date: 2018-10-08 14:25:30
 */
public enum NotifyStatusEnum implements Messageable {

	INIT("INIT", "未通知"),

	SUCCESS("SUCCESS", "通知成功"),

	FAIL("FAIL", "通知失败"),

	;

	private final String code;
	private final String message;

	private NotifyStatusEnum(String code, String message) {
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
		for (NotifyStatusEnum type : values()) {
			map.put(type.getCode(), type.getMessage());
		}
		return map;
	}

	/**
	 * 通过枚举值码查找枚举值。
	 * 
	 * @param code
	 *            查找枚举值的枚举值码。
	 * @return 枚举值码对应的枚举值。
	 * @throws IllegalArgumentException
	 *             如果 code 没有对应的 Status 。
	 */
	public static NotifyStatusEnum find(String code) {
		for (NotifyStatusEnum status : values()) {
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
	public static List<NotifyStatusEnum> getAll() {
		List<NotifyStatusEnum> list = new ArrayList<NotifyStatusEnum>();
		for (NotifyStatusEnum status : values()) {
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
		for (NotifyStatusEnum status : values()) {
			list.add(status.code());
		}
		return list;
	}

}

/*
* qiudot.com Inc.
* Copyright (c) 2018 All Rights Reserved.
* create by qiudot
* date:2018-10-08
*/
package com.qiudot.edu.web;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.qiudot.edu.entity.Trade;
import com.qiudot.edu.enums.GatewayStautsEnum;
import com.qiudot.edu.enums.TradeStatusEnum;
import com.qiudot.edu.enums.TradeTypeEnum;
import com.qiudot.edu.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 交易记录 管理控制器
 * 
 * @author qiudot
 * Date: 2018-10-08 14:25:30
 */
@Controller
@RequestMapping(value = "/manage/demo/trade")
public class TradeManagerController extends AbstractJQueryEntityController<Trade, TradeService> {
	

	{
		allowMapping = "*";
	}

	@SuppressWarnings("unused")
	@Autowired
	private TradeService tradeService;

	
	@Override
	protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
		model.put("allTradeTypes", TradeTypeEnum.mapping());
		model.put("allTradeStatuss", TradeStatusEnum.mapping());
		model.put("allGatewayStautss", GatewayStautsEnum.mapping());
	}

}

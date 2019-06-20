package com.qiudot.edu.service;

import com.qiudot.edu.service.base.dto.GatewayDeductPayDto;
import com.qiudot.edu.service.base.dto.GatewayTradeQueryDto;
import com.qiudot.edu.service.base.result.AliDeductPayResult;
import com.qiudot.edu.service.base.result.AliTradeQueryResult;

/**
 * @author zhike@acooly.com
 * @date 2018-10-09 10:10
 */

public interface AliInterfaceService {

    /**
     * 网关代扣
     * @param gatewayDeductPayDto
     * @return
     */
    AliDeductPayResult deductPay(GatewayDeductPayDto gatewayDeductPayDto);

    /**
     * 交易查询
     * @param gatewayTradeQueryDto
     * @return
     */
    AliTradeQueryResult tradeQuery(GatewayTradeQueryDto gatewayTradeQueryDto);
}

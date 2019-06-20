package com.qiudot.edu.service;

import com.qiudot.edu.service.base.order.WhdcGetPersonDetailsOrder;
import com.qiudot.edu.service.base.result.PageQueryTradeOrderDetailsResult;

/**
 * @author zhike@acooly.com
 * @date 2018-10-10 17:58
 */
public interface WhdcInterfaceService {

    /**
     * 查询用户消费记录
     *
     * @param pageQueryTradeOrderDetailsOrder
     */
    PageQueryTradeOrderDetailsResult queryTradeOrderDetails(WhdcGetPersonDetailsOrder pageQueryTradeOrderDetailsOrder);
}

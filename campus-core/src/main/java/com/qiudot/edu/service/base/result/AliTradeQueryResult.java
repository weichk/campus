package com.qiudot.edu.service.base.result;

import com.acooly.core.common.facade.ResultBase;
import com.acooly.core.utils.enums.Messageable;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhike@acooly.com
 * @date 2018-10-09 14:42
 */
@Getter
@Setter
public class AliTradeQueryResult extends ResultBase {

    /**
     * 银行响应报文
     */
    private String bankRespMessage;

    /**
     * 银行订单号
     */
    private String bankOrderNo;

    /**
     * 交易最终状态
     */
    private Messageable tradeStatus;
}

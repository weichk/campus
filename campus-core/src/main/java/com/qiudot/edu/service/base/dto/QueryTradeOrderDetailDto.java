package com.qiudot.edu.service.base.dto;

import com.acooly.core.common.facade.InfoBase;
import com.acooly.core.utils.Money;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhike@acooly.com
 * @date 2018-10-10 17:50
 */
@Getter
@Setter
public class QueryTradeOrderDetailDto extends InfoBase {

    /**
     * 交易标题（地点）
     */
    private String tradeTitle;

    /**
     * 消费金额
     */
    private Money amount;

    /**
     * 账户余额
     */
    private Money balance;

    /**
     * 交易时间
     */
    private String tradeTime;
}

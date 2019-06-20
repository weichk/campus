package com.qiudot.edu.service.base.dto;

import com.acooly.core.common.facade.InfoBase;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhike@acooly.com
 * @date 2018-10-10 18:22
 */
@Getter
@Setter
public class WhdcTradeItemsDto extends InfoBase {

    /**
     * 编号
     */
    private String number;

    /**
     * 姓名
     */
    private String name;

    /**
     * 日期
     */
    private String cdate;

    /**
     * 时间
     */
    private String ctime;

    /**
     * 终端编号
     */
    private String terminalno;

    /**
     * 金额
     */
    private String money;

    /**
     * 余额
     */
    private String balance;

    /**
     * 类型
     */
    private String moneytype;

    /**
     * 操作员
     */
    private String operater;

    /**
     * 说明
     */
    private String moneymemo;

}

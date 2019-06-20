package com.qiudot.edu.service.base.order;

import com.acooly.core.common.facade.PageOrder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author zhike@acooly.com
 * @date 2018-10-10 18:38
 */
@Getter
@Setter
public class PageQueryTradeDetailsOrder extends PageOrder {

    /**
     * 学校code
     */
    @NotBlank
    private String userNo;

    /**
     * 一卡通卡号
     */
    private String cardNo;

    /**
     * 学校编号
     */
    private String schoolNo;

    /**
     * 起始日期，格式：yyyy-mm-dd，例：2014-10-01。
     */
    private String beginDate;

    /**
     * 截止日期，格式：yyyy-mm-dd，例：2014-10-02。
     */
    private String endDate;

    /**
     *起始时间，格式：hh:nn，例：10:01。
     */
    private String begintime;

    /**
     * 截止时间，格式：hh:nn，例：12:00。
     */
    private String endtime;
}

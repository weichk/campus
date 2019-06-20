package com.qiudot.edu.service.base.order;

import com.acooly.core.common.facade.PageOrder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhike@acooly.com
 * @date 2018-10-10 18:38
 */
@Getter
@Setter
public class WhdcGetPersonDetailsOrder extends PageOrder {

    /**
     * 学校code
     */
    private String usercode;

    /**
     * 用户编码
     */
    private String personno;

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

    /**
     * 分页序号，从0开始计数，65535表示最后一页
     */
    private String pageIndex;

    /**
     * 每页返回最大条数。
     */
    private String itemmaxcount;

}

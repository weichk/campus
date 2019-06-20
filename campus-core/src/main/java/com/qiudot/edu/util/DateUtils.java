package com.qiudot.edu.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.Date;

/**
 * @author zhike@acooly.com
 * @date 2018-10-11 10:53
 */
@Slf4j
public class DateUtils {

    /**
     * 前几个月
     * @param date
     * @param months
     * @return
     */
    public static Date subMonth(Date date, int months) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, -months);
        return c.getTime();
    }

    /**
     * 前几周
     * @param date
     * @param weeks
     * @return
     */
    public static Date subWeek(Date date, int weeks) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.WEEK_OF_YEAR, -weeks);
        return c.getTime();
    }

}

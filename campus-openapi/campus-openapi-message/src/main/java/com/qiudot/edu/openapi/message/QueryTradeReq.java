package com.qiudot.edu.openapi.message;

import com.acooly.openapi.framework.common.annotation.OpenApiField;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author qiuboboy@qq.com
 * @date 2018-10-19 15:33
 */
@Getter
@Setter
@Slf4j
public class QueryTradeReq extends CampusReq {
    @NotEmpty
    @Size(max = 20)
    @OpenApiField(desc = "开始时间", constraint = "交易开始时间", demo = "2018-01-01 00:00:00")
    private Date begin;

    @NotEmpty
    @Size(max = 20)
    @OpenApiField(desc = "结束时间", constraint = "交易结束时间，不包括此时间", demo = "2018-02-01 00:00:00")
    private Date end;

    @NotNull
    @OpenApiField(desc = "页号", constraint = "第几页，从0开始，默认为第0页", demo = "0")
    private Integer page = 0;

    @NotNull
    @OpenApiField(desc = "每页条数", constraint = "每页条数，默认为10条", demo = "10")
    private Integer sizePerPage = 10;

    @NotNull
    @OpenApiField(desc = "降序排列查询结果", constraint = "是否按照时间降序查询结果", demo = "true")
    private Boolean desc;
}

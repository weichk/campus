package com.qiudot.edu.openapi.message;

import com.acooly.core.utils.Money;
import com.acooly.core.utils.enums.Messageable;
import com.acooly.openapi.framework.common.annotation.OpenApiField;
import com.acooly.openapi.framework.common.message.ApiResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author qiuboboy@qq.com
 * @date 2018-10-19 15:34
 */
@Slf4j
@Getter
@Setter
public class QueryTradeResp extends ApiResponse {
    @NotEmpty
    @OpenApiField(desc = "总条数", constraint = "总条数", demo = "10")
    private Long totalCount;

    @NotNull
    @OpenApiField(desc = "总充值金额", constraint = "总充值金额", demo = "6.66")
    private Money totalDeposit;

    @NotNull
    @OpenApiField(desc = "总消费金额", constraint = "总消费金额", demo = "6.66")
    private Money totalConsume;

    @NotNull
    @OpenApiField(desc = "交易明细", constraint = "交易明细")
    private List<TradeRecord> items;

    @Slf4j
    @Getter
    @Setter
    public static class TradeRecord {

        @OpenApiField(desc = "交易订单号", constraint = "交易订单号", demo = "12234")
        private String merchOrderNo;

        @NotNull
        @OpenApiField(desc = "交易金额", constraint = "交易金额", demo = "6.66")
        private Money amount;

        @NotNull
        @OpenApiField(desc = "交易时间", constraint = "交易时间", demo = "2018-02-01 00:00:00")
        private Date date;

        @OpenApiField(desc = "交易地点", constraint = "交易时间", demo = "清北大学第三食堂")
        private String location;

        @OpenApiField(desc = "交易备注", constraint = "交易备注", demo = "肥锅肉盖饭")
        private String memo;

        @NotNull
        @OpenApiField(desc = "交易后余额", constraint = "交易后余额", demo = "100.66")
        private Money balance;

        @NotNull
        @OpenApiField(desc = "交易类型", constraint = "交易类型", demo = "CONSUME")
        private TradeType tradeType;

        public static enum TradeType implements Messageable {
            DEPOSIT("DEPOSIT", "充值"), CONSUME("CONSUME", "消费");
            private final String code;
            private final String message;

            TradeType(String code, String message) {
                this.code = code;
                this.message = message;
            }

            public String getCode() {
                return code;
            }

            public String getMessage() {
                return message;
            }

            @Override
            public String code() {
                return code;
            }

            @Override
            public String message() {
                return message;
            }

        }

    }
}

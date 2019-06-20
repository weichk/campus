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
import javax.validation.constraints.Size;

/**
 * @author qiuboboy@qq.com
 * @date 2018-10-19 14:40
 */
@Slf4j
@Getter
@Setter
public class QueryUserInfoResp extends ApiResponse {
    @NotEmpty
    @Size(max = 10)
    @OpenApiField(desc = "用户姓名", constraint = "用户姓名", demo = "张三")
    private String name;

    @NotNull
    @OpenApiField(desc = "账户余额", constraint = "账户余额", demo = "6.66")
    private Money balance;

    @NotNull
    @OpenApiField(desc = "用户状态", constraint = "用户状态", demo = "NORMAL")
    private UserStatus status;

    public static enum UserStatus implements Messageable {
        NORMAL("NORMAL", "正常"), FREEZE("FREEZE", "冻结"), LOSS("LOSS", "挂失"), CANCEL("CANCEL", "注销");
        private final String code;
        private final String message;

        UserStatus(String code, String message) {
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

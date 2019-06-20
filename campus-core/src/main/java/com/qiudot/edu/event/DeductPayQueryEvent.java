package com.qiudot.edu.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @Auther: zhike
 * @Date: 2018/8/31 16:10
 * @Description:
 */
@Getter
@Setter
@ToString
public class DeductPayQueryEvent implements Serializable {

    /**
     * 业务订单号
     */
    @NotBlank
    private String bizOrderNo;


    public DeductPayQueryEvent() {
    }

    public DeductPayQueryEvent(String bizOrderNo) {
        this.bizOrderNo = bizOrderNo;
    }
}

package com.qiudot.edu.test.openapi;

import com.acooly.core.utils.Money;
import com.acooly.openapi.framework.common.utils.Ids;
import com.qiudot.edu.openapi.message.DeductPayRequest;
import com.qiudot.edu.openapi.message.DeductPayResponse;
import com.qiudot.edu.test.AbstractDassetsApiTestBase;
import org.junit.Test;

/**
 * @Auther: zhike
 * @Date: 2018/8/16 20:30
 * @Description:
 */
public class TestOpenapiService extends AbstractDassetsApiTestBase {

    /**
     * 测试充值
     */
    @Test
    public void deductPay() {
        service = "deductPay";
        DeductPayRequest request = new DeductPayRequest();
        request.setService(service);
        request.setRequestNo(Ids.oid("REQU"));
        request.setMerchOrderNo(Ids.oid());
        request.setUserNo("6666");
        request.setSchoolNo("3");
        request.setSubject("午餐消费");
        request.setAmount(Money.amout("0.01"));
        request.setMemo("充值");
        request(request, DeductPayResponse.class);
    }
}

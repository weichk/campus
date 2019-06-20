package com.qiudot.edu.openapi.service;

import com.acooly.core.common.facade.ResultBase;
import com.acooly.core.utils.Ids;
import com.acooly.openapi.framework.common.annotation.OpenApiNote;
import com.acooly.openapi.framework.common.annotation.OpenApiService;
import com.acooly.openapi.framework.common.enums.ResponseType;
import com.acooly.openapi.framework.core.service.base.BaseApiService;
import com.qiudot.edu.openapi.message.DeductPayRequest;
import com.qiudot.edu.openapi.message.DeductPayResponse;
import com.qiudot.edu.service.TradeService;
import com.qiudot.edu.service.base.dto.TradeDeductPayDto;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zhike 2018/8/1 22:05
 */
@OpenApiNote("支付宝代扣")
@OpenApiService(name = "deductPay",desc = "代扣消费",responseType = ResponseType.SYN,owner = "zhike")
public class DeductPayService extends BaseApiService<DeductPayRequest, DeductPayResponse> {

    @Autowired
    private TradeService tradeService;

    @Override
    protected void doService(DeductPayRequest request, DeductPayResponse response) {
        TradeDeductPayDto tradeDeductPayDto = new TradeDeductPayDto();
        tradeDeductPayDto.setAmount(request.getAmount());
        tradeDeductPayDto.setBizOrderNo(Ids.oid());
        tradeDeductPayDto.setCardNo(request.getUserNo());
        tradeDeductPayDto.setMerchOrderNo(request.getMerchOrderNo());
        tradeDeductPayDto.setSubject(request.getSubject());
        tradeDeductPayDto.setSchoolNo(request.getSchoolNo());
        tradeDeductPayDto.setPartnerId(request.getPartnerId());
        ResultBase resultBase = tradeService.deductPay(tradeDeductPayDto);
        resultBase.throwIfNotSuccess();
    }
}

package com.qiudot.edu.service.impl;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.facade.ResultCode;
import com.acooly.core.utils.Strings;
import com.acooly.core.utils.enums.ResultStatus;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AgreementParams;
import com.alipay.api.domain.AlipayTradePayModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.qiudot.edu.enums.AliResCodeEnum;
import com.qiudot.edu.enums.AliTradeQueryStatusEnum;
import com.qiudot.edu.service.AliInterfaceService;
import com.qiudot.edu.service.base.dto.GatewayDeductPayDto;
import com.qiudot.edu.service.base.dto.GatewayTradeQueryDto;
import com.qiudot.edu.service.base.result.AliDeductPayResult;
import com.qiudot.edu.service.base.result.AliTradeQueryResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhike@acooly.com
 * @date 2018-10-09 10:11
 */
@Slf4j
@Service
public class AliInterfaceServiceImpl implements AliInterfaceService {

    @Autowired
    private AlipayClient alipayClient;

    @Override
    public AliDeductPayResult deductPay(GatewayDeductPayDto gatewayDeductPayDto) {
        AliDeductPayResult result = new AliDeductPayResult();
        try {
            AlipayTradePayRequest request = new AlipayTradePayRequest();
            request.putOtherTextParam("app_auth_token", gatewayDeductPayDto.getSchoolAnthCode());
            AlipayTradePayModel model = new AlipayTradePayModel();
            model.setOutTradeNo(gatewayDeductPayDto.getBizOrderNo());
            model.setProductCode("GENERAL_WITHHOLDING");
            model.setTotalAmount(gatewayDeductPayDto.getAmount().toString());
            model.setSubject(gatewayDeductPayDto.getSubject());
            AgreementParams agreementParams = new AgreementParams();
            agreementParams.setAgreementNo(gatewayDeductPayDto.getAgreementNo());
            model.setAgreementParams(agreementParams);
            request.setBizModel(model);
            AlipayTradePayResponse response = alipayClient.execute(request);
            log.info("支付宝代扣响应报文：{}", JSON.toJSONString(response));
            result.setBankRespMessage(JSON.toJSONString(response));
            if (response.isSuccess()) {
                if (Strings.equals(response.getCode(), AliResCodeEnum.SUCCESS.getCode())) {
                    result.setStatus(ResultStatus.success);
                    result.setCode(ResultCode.SUCCESS.getCode());
                    result.setDetail("代扣成功");
                    result.setBankOrderNo(response.getTradeNo());
                    result.setTradeStatus(ResultStatus.success);
                } else if (Strings.equals(response.getCode(), AliResCodeEnum.PROCESSING.getCode())) {
                    result.markProcessing();
                    result.setTradeStatus(ResultStatus.processing);
                } else {
                    result.setStatus(ResultStatus.failure);
                    result.setDetail("支付宝代扣失败");
                    result.setCode(ResultCode.FAILURE.getCode());
                    result.setTradeStatus(ResultStatus.failure);
                }
            } else {
                result.setStatus(ResultStatus.failure);
                result.setDetail("支付宝代扣响应失败");
                result.setCode(ResultCode.FAILURE.getCode());
                result.setTradeStatus(ResultStatus.processing);
            }
        } catch (AlipayApiException e) {
            throw new BusinessException(e.getErrCode(), "代扣支付请求异常：" + e.getErrMsg());
        }
        return result;
    }

    @Override
    public AliTradeQueryResult tradeQuery(GatewayTradeQueryDto gatewayTradeQueryDto) {
        AliTradeQueryResult result = new AliTradeQueryResult();
        try {
            AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
            AlipayTradeQueryModel alipayTradeQueryModel = new AlipayTradeQueryModel();
            alipayTradeQueryModel.setOutTradeNo(gatewayTradeQueryDto.getBizOrderNo());
            request.setBizModel(alipayTradeQueryModel);
            AlipayTradeQueryResponse response = alipayClient.execute(request);
            log.info("支付宝交易查询响应报文：{}", JSON.toJSONString(response));
            result.setBankRespMessage(JSON.toJSONString(response));
            if (response.isSuccess()) {
                if (response.getTradeStatus().equals(AliTradeQueryStatusEnum.TRADE_SUCCESS.getCode())) {
                    result.setStatus(ResultStatus.success);
                    result.setCode(ResultCode.SUCCESS.getCode());
                    result.setDetail("查询代扣成功");
                    result.setBankOrderNo(response.getTradeNo());
                    result.setTradeStatus(ResultStatus.success);
                } else if (response.getTradeStatus().equals(AliTradeQueryStatusEnum.WAIT_BUYER_PAY.getCode())) {
                    result.markProcessing();
                    result.setTradeStatus(ResultStatus.processing);
                } else {
                    result.setStatus(ResultStatus.failure);
                    result.setDetail("支付宝代扣失败");
                    result.setCode(ResultCode.FAILURE.getCode());
                    result.setTradeStatus(ResultStatus.failure);
                }
            } else {
                result.setStatus(ResultStatus.failure);
                result.setDetail("支付宝交易查询响应失败");
                result.setCode(ResultCode.FAILURE.getCode());
                result.setTradeStatus(ResultStatus.processing);
            }
        } catch (AlipayApiException e) {
            log.info("交易订单查询请求异常：errCode={},errMsg={}",e.getErrCode(), e.getErrMsg());
        }
        return result;
    }
}

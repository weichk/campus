package com.qiudot.edu.test;

import com.acooly.core.utils.Ids;
import com.acooly.core.utils.Money;
import com.acooly.core.utils.ToString;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.*;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.request.AlipayUserAgreementPageSignRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayTradePayResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.alipay.api.response.AlipayUserAgreementPageSignResponse;
import com.qiudot.edu.client.whdc.WebServiceClient;
import com.qiudot.edu.config.CampusProperties;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URLEncoder;
import java.util.Map;

/**
 * @author qiuboboy@qq.com
 * @date 2018-09-25 15:56
 */
@Slf4j
public class AlipayTest extends TestBase {
    @Autowired
    private AlipayClient alipayClient;
    @Autowired
    private CampusProperties campusProperties;
    @Autowired
    private WebServiceClient webServiceClient;

    /**
     * 1. 调用testGenerateUrl 生成url，用户点击此url并登录
     * 2. alipay重定向到我们设置的${campus.getAlipayUserIdRediectUrl},在参数中获取auth_code
     * 3. 调用AlipaySystemOauthToken接口获取用户id
     * https://www.baidu.com/?app_id=2016092200570385&source=alipay_wallet&scope=auth_base&auth_code=41d52e1137f446b1a8842f905b5dND01
     *
     * @throws Exception
     */
    @Test
    public void testGetAlipayUserID() throws Exception {
        AlipaySystemOauthTokenRequest oauthTokenRequest = new AlipaySystemOauthTokenRequest();
        oauthTokenRequest.setCode("41d52e1137f446b1a8842f905b5dND01");
        oauthTokenRequest.setGrantType("authorization_code");
        AlipaySystemOauthTokenResponse response = alipayClient.execute(oauthTokenRequest);
        if (response.isSuccess()) {
            log.info("{}", response.getBody());
            log.info("userId:{}", response.getUserId());
        } else {
            log.info("{}", response.getSubMsg());
        }
    }

    @Test
    public void name() throws AlipayApiException {
        AlipayUserAgreementPageSignRequest request = new AlipayUserAgreementPageSignRequest();
        AlipayUserAgreementPageSignModel model = new AlipayUserAgreementPageSignModel();
        AccessParams accessParams = new AccessParams();
        accessParams.setChannel("ALIPAYAPP");
        model.setAccessParams(accessParams);
        model.setPersonalProductCode("GENERAL_WITHHOLDING_P");
        model.setSignScene("INDUSTRY|EDU_AUTODEPOSIT");
        request.setBizModel(model);
        AlipayUserAgreementPageSignResponse response = alipayClient.pageExecute(request);
        if (response.isSuccess()) {
            log.info("{}", response.getBody());
            log.info("userId:{}", ToString.toString(response));
        } else {
            log.info("{}", response.getSubMsg());
        }
    }

    @Test
    public void name1() throws Exception {

        AlipayUserAgreementPageSignRequest request = new AlipayUserAgreementPageSignRequest();
        AlipayUserAgreementPageSignModel model = new AlipayUserAgreementPageSignModel();

        AccessParams accessParams = new AccessParams();
        accessParams.setChannel("ALIPAYAPP");
        model.setAccessParams(accessParams);
//        model.setExternalAgreementNo("mayin201801122040002");
//        model.setExternalLogonId("15912345678");
        model.setPersonalProductCode("GENERAL_WITHHOLDING_P");
        model.setSignScene("INDUSTRY|EDU");
        model.setThirdPartyType("PARTNER");

        request.setBizModel(model);

        AlipayUserAgreementPageSignResponse response = alipayClient.pageExecute(request, "GET");
        StringBuffer bufferBody = new StringBuffer(response.getBody());
        String schemeBody = bufferBody.substring(bufferBody.indexOf("?") + 1);
        log.info("schemeBody: {}", schemeBody);
        String encodeSchemeBody = URLEncoder.encode(schemeBody, "utf-8");
        log.info("encodeSchemeBody: {}", encodeSchemeBody);
        String realScheme = "alipays://platformapi/startapp?appId=60000157&appClearTop=false&startMultApp=YES&sign_params=" + encodeSchemeBody;
        log.info("realScheme: {}", realScheme);
    }

    @Test
    public void testPay() throws AlipayApiException {
        AlipayTradePayRequest request = new AlipayTradePayRequest();
        AlipayTradePayModel model = new AlipayTradePayModel();
        AgreementParams agreementParams=new AgreementParams();
        agreementParams.setAgreementNo("20185809472197107778");
        model.setOutTradeNo(Ids.gid());
        model.setSubject("午餐:红烧肉");
        model.setAgreementParams(agreementParams);
        model.setBuyerId("2088002047133783");
        model.setProductCode("GENERAL_WITHHOLDING");
        ExtendParams extendParams=new ExtendParams();
        extendParams.setSysServiceProviderId(campusProperties.getPid());
        model.setExtendParams(extendParams);
        request.setBizModel(model);
        model.setTotalAmount("0.01");
        request.putOtherTextParam("app_auth_token", "");
        AlipayTradePayResponse response = alipayClient.execute(request);
        log.info("{}", ToString.toString(response));
    }

    @Test
    public void testPayRefund() throws AlipayApiException {
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        model.setOutTradeNo("o18101817103575300001");
        model.setRefundAmount(Money.amout("0.01").toString());
        ExtendParams extendParams=new ExtendParams();
        extendParams.setSysServiceProviderId(campusProperties.getPid());
        request.setBizModel(model);
        AlipayTradeRefundResponse response = alipayClient.execute(request);
        log.info("{}", ToString.toString(response));
    }


    @Test
    public void testGetAlipayOAuthUrl() {
        log.info("{}",campusProperties.getAlipayOAuthUrl());
    }

    @Test
    public void testGenerateUrl() {
        log.info("{}", campusProperties.getGetAlipayUserIdOauth2Url());
    }

    @Test
    public void testWHDC() {
        Map<String, String> result = webServiceClient.getPersonInfo("3", "8888");
        log.info("{}", result);
        webServiceClient.ZFB_BindDoc("3", "8888", "李红梅", "18580000000", "123456",
                "11010110081111", "2018002200570385", "http://www.baidu.com", "云南", "昆明", "xx", "y", "2", "T", "T", "F");
        Map<String, Object> map1 = webServiceClient.GetPersonDetails("3", "8888", "2014-10-01", "2018-10-02", "10:01", "12:00", "0", "100");
        log.info("{}", map1);
        webServiceClient.ZFB_UnBindDoc("3", "8888", "李红梅", "123456", "2018002200570385");
    }

    @Test
    public void testGetTradeDetails() {
        Map<String, Object> map1 = webServiceClient.GetPersonDetails("3", "8888", "2018-08-01", "2018-8-31", "00:00", "23:59", "0", "100");
        log.info("五河大成用户消费记录{}", map1);
    }
}

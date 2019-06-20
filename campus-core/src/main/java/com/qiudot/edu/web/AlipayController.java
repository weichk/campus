package com.qiudot.edu.web;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.type.DBMap;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.core.utils.*;
import com.acooly.core.utils.enums.Messageable;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AccessParams;
import com.alipay.api.domain.AlipayOpenAuthTokenAppModel;
import com.alipay.api.domain.AlipayUserAgreementPageSignModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayOpenAuthTokenAppRequest;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserAgreementPageSignRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipayOpenAuthTokenAppResponse;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserAgreementPageSignResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.qiudot.edu.client.whdc.WebServiceClient;
import com.qiudot.edu.config.CampusProperties;
import com.qiudot.edu.entity.Customer;
import com.qiudot.edu.entity.CustomerCard;
import com.qiudot.edu.entity.Shool;
import com.qiudot.edu.enums.CardStatusEnum;
import com.qiudot.edu.service.CustomerCardService;
import com.qiudot.edu.service.CustomerService;
import com.qiudot.edu.service.ShoolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Map;

import static com.acooly.core.utils.Dates.CHINESE_DATETIME_FORMAT_LINE;

/**
 * @author qiuboboy@qq.com
 * @date 2018-10-08 18:55
 */
@Slf4j
@Controller
@RequestMapping(value = "alipay")
public class AlipayController {
    @Autowired
    private AlipayClient alipayClient;
    @Autowired
    private CampusProperties campusProperties;
    @Autowired
    private ShoolService shoolService;
    @Autowired
    private CustomerService customerService;


    /**
     * 用户代扣签约
     *
     * @return
     */
    @RequestMapping("/userAgreementSign")
    public String userAgreementSign(String schoolCode) {
        AlipayUserAgreementPageSignRequest request = new AlipayUserAgreementPageSignRequest();
        AlipayUserAgreementPageSignModel model = new AlipayUserAgreementPageSignModel();
        AccessParams accessParams = new AccessParams();
        accessParams.setChannel("ALIPAYAPP");
        model.setAccessParams(accessParams);
        model.setPersonalProductCode("GENERAL_WITHHOLDING_P");
        model.setSignScene("INDUSTRY|EDU");
        model.setThirdPartyType("PARTNER");
        request.setBizModel(model);
        if (!StringUtils.isEmpty(schoolCode)) {
            Shool school = shoolService.findBySchoolCode(schoolCode);
            request.putOtherTextParam("app_auth_token", school.getAliAuthCode());
        } else {
            request.putOtherTextParam("app_auth_token", "");
        }
        request.setNotifyUrl(campusProperties.getUserAgreementSignNotifyUrl());
        AlipayUserAgreementPageSignResponse response = null;
        try {
            response = alipayClient.pageExecute(request, "GET");
            StringBuffer bufferBody = new StringBuffer(response.getBody());
            String schemeBody = bufferBody.substring(bufferBody.indexOf("?") + 1);
            log.info("schemeBody: {}", schemeBody);
            String encodeSchemeBody = URLEncoder.encode(schemeBody, "utf-8");
            log.info("encodeSchemeBody: {}", encodeSchemeBody);
            String realScheme = "alipays://platformapi/startapp?appId=60000157&appClearTop=false&startMultApp=YES&sign_params=" + encodeSchemeBody;
            log.info("realScheme: {}", realScheme);
            if (response.isSuccess()) {
                log.info("代扣签约返回数据：{}", response.getBody());
                //重定向到绑学生卡
                // return "redirect:/mobile/service/school/list.html";
                return "redirect:" + response.getBody();
            } else {
                throw new BusinessException(response.getSubMsg());
            }
        } catch (Exception e) {
            log.error("", e);
            throw Exceptions.rethrowBusinessException(e);
        }
    }

    /**
     * 代扣签约回调通知
     *
     * @param request
     * @param httpServletResponse
     * @param charset
     * @param sign_type
     */
    @RequestMapping("/userAgreementSignCallBack")
    public void userAgreementSignCallBack(HttpServletRequest request, HttpServletResponse httpServletResponse, String charset, String sign_type) {
        Map<String, String> parameters = Servlets.getParameters(request);
        log.info("支付宝代扣签约异步回调参数:{}", parameters);
        // parameters:{notify_time=2018-10-09 15:05:12, agreement_no=20185809472068057778, status=NORMAL, sign_type=RSA2, charset=utf-8,
        // auth_app_id=2018100861588693, notify_type=dut_user_sign, alipay_user_id=2088002047133783,
        // login_token=abbec6b164d5b441931a18280dc6e60e_78, invalid_time=2115-02-01 00:00:00, personal_product_code=GENERAL_WITHHOLDING_P,
        // version=1.0, sign=HNjcUpNBC/h2NCgO5PFWz0w+iF788VBCYBe+j3cW4oQXwg0uSVzkyRBmctXJqz/37NhjQ+FryxhOkfgGx0IABeTFyaagZrldEqeuSSBuvifBZSizjFEBvEcWvB0d7RCvx0FzABnF80gfgabkoNjg2+0rekrJ1Lp58+0MwzJ/Cbgccy9BMIVEneJr7NblHMBGnBjCgRc0iASAgWMK7baF0oK4xtiAY/jcCgayGnYPNyefFhWNQ5qsr4sNNqOLf+NaSBkwSWXLVrq5KVtwc5Ht9K25QbL0lM756lo2QxxCp5jYGUGzOUb5eENE/ZFshbORD0VpjQwxnWdI3nu3+x8QyA==, sign_scene=INDUSTRY|EDU, valid_time=2018-10-09 15:05:12, app_id=2018100861588693, alipay_logon_id=boh***@qq.com, notify_id=2018100900222150512041760506760295, sign_time=2018-10-09 15:05:12}
        boolean verify_result = false;
        try {
            verify_result = AlipaySignature.rsaCheckV1(parameters, campusProperties.getAlipayPublicKeyContent(), charset, sign_type);
        } catch (AlipayApiException e) {
            log.error("", e);
            throw Exceptions.rethrowBusinessException(e);
        }
        if (verify_result) {
            String auth_app_id = parameters.get("auth_app_id");
            String alipay_user_id = parameters.get("alipay_user_id");
            String agreement_no = parameters.get("agreement_no");
            String sign_time = parameters.get("sign_time");

            log.info("支付宝代扣签约,验签成功,用户名{},auth_app_id：{},alipay_user_id:{}", parameters.get("alipay_logon_id"), auth_app_id, alipay_user_id);

            Customer customer = customerService.findByAlipayUserId(alipay_user_id);
            if (customer == null) {
                customer = new Customer();
                customer.setUserNo(Ids.oid());
            }
            customer.setAuthAppId(auth_app_id);
            customer.setAlipayUserId(alipay_user_id);
            customer.setAgreementNo(agreement_no);
            if (!StringUtils.isEmpty(sign_time)) {
                customer.setSignTime(Dates.parse(sign_time, CHINESE_DATETIME_FORMAT_LINE));
            }
            customerService.saveOrUpdate(customer);
            log.info("支付宝代扣签约入库成功,用户名{},auth_app_id：{},alipay_user_id:{}", parameters.get("alipay_logon_id"), auth_app_id, alipay_user_id);
            Servlets.writeText(httpServletResponse, "success");
        } else {
            log.info("支付宝代扣签，验签失败");
            Servlets.writeText(httpServletResponse, "fail");
        }

    }

    @RequestMapping("/userBindCard")
    public String userBindCard() {
        log.info("请求用户绑卡");
        return "redirect:" + campusProperties.getGetAlipayUserIdOauth2Url();
    }

    @RequestMapping("/userBindCardGetUserInfo")
    public void userBindCardGetUserInfo(String auth_code, HttpServletResponse httpServletResponse) {
        log.info("请求用户绑卡-获取用户信息");
        AlipaySystemOauthTokenRequest oauthTokenRequest = new AlipaySystemOauthTokenRequest();
        oauthTokenRequest.setCode(auth_code);
        oauthTokenRequest.setGrantType("authorization_code");
        AlipaySystemOauthTokenResponse response = null;
        try {
            response = alipayClient.execute(oauthTokenRequest);
            if (response.isSuccess()) {
                String userId = response.getUserId();
                AlipayUserInfoShareRequest request = new AlipayUserInfoShareRequest();
                AlipayUserInfoShareResponse alipayUserInfoShareResponse = alipayClient.execute(request, response.getAccessToken());
                log.info("body:{}", alipayUserInfoShareResponse.getBody());
                Customer customer = customerService.findByAlipayUserId(userId);
                if (customer == null) {
                    customer = new Customer();
                    customer.setUserNo(Ids.oid());
                    customer.setAlipayUserId(userId);
                }
                DBMap ext = customer.getExtJson();
                if (ext == null) {
                    ext = new DBMap();
                }
                ext.put("avatar", alipayUserInfoShareResponse.getAvatar());
                ext.put("province", alipayUserInfoShareResponse.getProvince());
                ext.put("city", alipayUserInfoShareResponse.getCity());
                ext.put("nick_name", alipayUserInfoShareResponse.getNickName());
                ext.put("is_student_certified", alipayUserInfoShareResponse.getIsStudentCertified());
                ext.put("user_type", alipayUserInfoShareResponse.getUserType());
                ext.put("user_status", alipayUserInfoShareResponse.getUserStatus());
                ext.put("is_certified", alipayUserInfoShareResponse.getIsCertified());
                ext.put("gender", alipayUserInfoShareResponse.getGender());
                customer.setExtJson(ext);
                customer.setCertNo(alipayUserInfoShareResponse.getCertNo());
                customerService.saveOrUpdate(customer);
                Servlets.writeText(httpServletResponse, "success");
            } else {
                log.info("{}", response.getSubMsg());
                Servlets.writeText(httpServletResponse, "fail");
            }
        } catch (AlipayApiException e) {
            log.error("", e);
            Servlets.writeText(httpServletResponse, e.getMessage());
            throw Exceptions.rethrowBusinessException(e);
        }
    }

    /**
     * 商户授权回调
     *
     * @param app_auth_code
     * @param app_id
     * @param request
     * @param httpServletResponse
     */
    @RequestMapping("/oauthRedirect")
    public void schoolAgreementSignCallBack(String app_auth_code, String app_id, HttpServletRequest request, HttpServletResponse httpServletResponse) {
        /**
         *
         * 授权步骤
         *
         * 1. 生成授权二维码
         * 2. 商户扫码授权
         * 3. 回推授权,获取app_auth_code(此通知)
         * 4. 使用app_auth_code换取app_auth_token
         * 5. app_auth_token永久有效，保存app_auth_token
         */
        try {
            Map<String, String> parameters = Servlets.getParameters(request);
            log.info("支付宝学校签约异步回调参数:{}", parameters);

            log.info("收到授权回推消息,app_id:{},app_auth_code:{}", app_id, app_auth_code);
            String appId = campusProperties.getAppId();
            if (!appId.equals(app_id)) {
                throw new BusinessException("授权收到的appId与开发appId不一致");
            }

            //4. 使用app_auth_code换取app_auth_token
            AlipayOpenAuthTokenAppRequest tokenrequest = new AlipayOpenAuthTokenAppRequest();
            AlipayOpenAuthTokenAppModel appModel = new AlipayOpenAuthTokenAppModel();
            appModel.setGrantType("authorization_code");
            appModel.setCode(app_auth_code);
            tokenrequest.setBizModel(appModel);
            AlipayOpenAuthTokenAppResponse response = alipayClient.execute(tokenrequest);
            log.info("换取app_auth_token请求：{}", response.getBody());

            //商户授权令牌
            String appAuthToken = response.getAppAuthToken();
            //授权商户的ID
            String userId = response.getUserId();
            //授权商户的AppId
            String authAppId = response.getAuthAppId();

            log.info("收到商户userId{},appId:{},授权令牌:{}", userId, authAppId, appAuthToken);

            if (StringUtils.isEmpty(authAppId)) {
                throw new BusinessException("未返回商户的AppId");
            }
            if (StringUtils.isEmpty(appAuthToken)) {
                throw new BusinessException("未返回商户的授权令牌");
            }
            Shool shool = shoolService.findByPid(userId);
            if (shool == null) {
                throw new BusinessException("商户userId:" + userId + ",appId:" + authAppId + "，还未注册到支付宝，先注册后授权");
            }
            shool.setAliAuthCode(appAuthToken);
            shoolService.update(shool);
            log.info("商户{},{},appId:{},授权令牌:{}保存成功", shool.getShoolName(), shool.getShoolCode(), authAppId, appAuthToken);

        } catch (Exception e) {
            Servlets.writeText(httpServletResponse, "fail");
            log.error("商户授权令牌获取失败", e);
        }

    }


}

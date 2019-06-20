package com.qiudot.edu.client.whdc;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.utils.Exceptions;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.qiudot.edu.config.CampusProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author qiuboboy@qq.com
 * @date 2018-09-28 13:22
 */
@Slf4j
public class WebServiceClient extends WebServiceGatewaySupport {

    @Autowired
    private CampusProperties campusProperties;

    private Map<String, ?> invoke(String method, String... args) {
        log.info("调用五和大成WS:method:{},args:{}", method, args);
        try {
            WHWebServiceWHDCIF request = new WHWebServiceWHDCIF();
            request.setParams(buildRequestBody(method, args));
            WHWebServiceWHDCIFResponse response = (WHWebServiceWHDCIFResponse) getWebServiceTemplate()
                    .marshalSendAndReceive(campusProperties.getEcard().getWhdc().getServiceUrl(), request);
            WSResult wsResult = buildResponseBody(response);
            wsResult.throwIfFailure();
            log.info("调用五和大成WS成功:{}", wsResult.getResult());
            return wsResult.getResult();
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                BusinessException businessException = (BusinessException) e;
                log.error("调用五和大成WS失败,code:{},msg:{}", businessException.getCode(), businessException.getMessage());
                throw businessException;
            } else {
                log.error("调用五和大成WS失败", e);
                throw e;
            }
        }
    }

    private String getAccessToken(String usercode) {
        return (String) invoke("GetAccessToken", usercode).get("accesstoken");
    }

    private WSResult buildResponseBody(WHWebServiceWHDCIFResponse response) {
        String result = response.getResult();
        return JSON.parseObject(result, WSResult.class);
    }

    private String buildRequestBody(String method, String... args) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("method", method);
        params.put("args", args);
        params.put("sign", buildSign(method, args));
        return JSON.toJSONString(params);
    }

    private String buildSign(String method, String[] args) {
        StringBuilder signStr = new StringBuilder(method);
        for (String arg : args) {
            signStr.append("|");
            signStr.append(arg);
        }
        signStr.append("|").append(campusProperties.getEcard().getWhdc().getWebServiceKey());
        try {
            return DigestUtils.md5Hex(signStr.toString().getBytes("GB2312"));
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
            throw Exceptions.rethrowBusinessException(e);
        }
    }

    public Map<String, String> getPersonInfo(String usercode, String personno) {
        String accessToken = this.getAccessToken(usercode);
        Map<String, String> result = (Map<String, String>) this.invoke("GetPersonInfo", usercode, accessToken, personno);
        return result;
    }

    public void ZFB_BindDoc(String usercode, String personno, String personname, String telephone, String password, String identityid, String user_id, String avatar, String province, String city, String nick_name, String is_student_certified, String user_type, String user_status, String is_certified, String gender) {
        String accessToken = this.getAccessToken(usercode);
        Map<String, String> result = null;
        try {
            result = (Map<String, String>) this.invoke("ZFB_BindDoc", usercode, accessToken, personno, personname, telephone, password, identityid, user_id, avatar, province, city, nick_name, is_student_certified, user_type, user_status, is_certified, gender);
        } catch (BusinessException e) {
            if (e.getCode().equals("9972")) {
                log.info(e.getMessage());
            } else {
                throw e;
            }
        }
    }

    public Map<String, Object> GetPersonDetails(String usercode, String personno, String begindate, String enddate, String begintime, String endtime, String pageindex, String itemmaxcount) {
        String accessToken = this.getAccessToken(usercode);
        Map<String, Object> result = (Map<String, Object>) this.invoke("GetPersonDetails", usercode, accessToken, personno, begindate, enddate, begintime, endtime, pageindex, itemmaxcount);
        return result;
    }

    public  void ZFB_UnBindDoc(String usercode, String personno, String personname, String password, String user_id) {
        String accessToken = this.getAccessToken(usercode);
        try {
            this.invoke("ZFB_UnBindDoc", usercode, accessToken, personno, personname, password, user_id);
        } catch (BusinessException e) {
            if (e.getCode().equals("9975")) {
                log.info(e.getMessage());
            } else {
                throw e;
            }
        }
    }
}

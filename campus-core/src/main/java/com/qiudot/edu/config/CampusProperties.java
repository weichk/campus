package com.qiudot.edu.config;

import com.acooly.core.common.exception.AppConfigException;
import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * @author qiuboboy@qq.com
 * @date 2018-09-25 15:41
 */
@Data
@ConfigurationProperties(prefix = "campus")
@Slf4j
public class CampusProperties {
    private Ecard ecard = new Ecard();

    /**
     * 本平台服务访问基础路径
     */
    private String baseUrl;
    private String sessionUserKey = "sessionUser";
    /**
     * 支付宝openapi接口地址
     */
    private String serverUrl;
    private String appId;
    private String pid;
    private String oauthRedirectUrl;
    private Resource privateKey;
    private Resource publicKey;
    private Resource alipayPublicKey;


    private String getAlipayUserIdOauth2Url;

    /**
     * 用户成功签约通知页面
     */
    private String userAgreementSignNotifyUrl;
    /**
     * 学校成功签约通知页面
     */
    private String schoolAgreementSignNotifyUrl;

    /**
     * 学校授权url前缀
     */
    private String alipayAuthUrlPrefix;

    private boolean mockWHDCApi = false;

    public String getPrivateKeyContent() {
        try {
            return new String(ByteStreams.toByteArray(privateKey.getInputStream()), Charsets.UTF_8);
        } catch (IOException e) {
            throw new AppConfigException(e);
        }
    }

    public String getPublicKeyContent() {
        try {
            return new String(ByteStreams.toByteArray(publicKey.getInputStream()), Charsets.UTF_8);
        } catch (IOException e) {
            throw new AppConfigException(e);
        }
    }

    public String getAlipayPublicKeyContent() {
        try {
            return new String(ByteStreams.toByteArray(alipayPublicKey.getInputStream()), Charsets.UTF_8);
        } catch (IOException e) {
            throw new AppConfigException(e);
        }
    }

    public String getAlipayOAuthUrl() {
        return "https://openauth.alipay.com/oauth2/appToAppAuth.htm?app_id=" + this.getAppId() + "&redirect_uri=" + URLEncoder.encode(oauthRedirectUrl);
    }

    public String getAlipayLoginUrl(String url) {
        return "https://openauth.alipay.com/oauth2/publicAppAuthorize.htm?app_id=" + this.getAppId() + "&scope=auth_user&redirect_uri=" + URLEncoder.encode(url);
    }

    /**
     * 一卡通配置
     */
    @Data
    public static class Ecard {

        private WHDC whdc = new WHDC();

        /**
         * 五和大成
         */
        @Data
        public static class WHDC {

            private String serviceUrl;

            private String webServiceKey;


        }
    }
}

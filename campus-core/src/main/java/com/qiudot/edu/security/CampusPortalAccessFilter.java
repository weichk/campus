package com.qiudot.edu.security;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.utils.Servlets;
import com.acooly.core.utils.Strings;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.qiudot.edu.config.CampusProperties;
import com.qiudot.edu.entity.Customer;
import com.qiudot.edu.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Order(50)
@WebFilter(filterName = "campusPortalLogin", urlPatterns = "/mobile/service/*")
public class CampusPortalAccessFilter implements Filter {

    @Autowired
    private AlipayClient alipayClient;
    @Autowired
    private CampusProperties campusProperties;
    @Autowired
    private CustomerService CustomerService;

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        log.info("=====start filter campusPortalLogin");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String requestPath = campusProperties.getBaseUrl() + request.getRequestURI() + (Strings.isNotBlank(request.getQueryString()) ? "?" + request.getQueryString() : "");
        try {
            SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(campusProperties.getSessionUserKey());
            String authCode = Servlets.getParameter(request, "auth_code");

            if (sessionUser == null && Strings.isBlank(authCode)) {
                String alipayLoginUrl = campusProperties.getAlipayLoginUrl(requestPath);
                log.info("[认证登录]未登录,跳转支付宝验证:{}", alipayLoginUrl);
                response.sendRedirect(alipayLoginUrl);
                return;
            } else if (sessionUser == null && Strings.isNotBlank(authCode)) {
                //利用authCode获得authToken
                try {
                    AlipaySystemOauthTokenRequest oauthTokenRequest = new AlipaySystemOauthTokenRequest();
                    oauthTokenRequest.setCode(authCode);
                    oauthTokenRequest.setGrantType("authorization_code");
                    AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(oauthTokenRequest);
                    Customer customer = CustomerService.findByAlipayUserId(oauthTokenResponse.getUserId());
                    if (customer == null) {
                        String bindUrl = campusProperties.getBaseUrl() + "/alipay/userBindCard";
                        log.info("用户未注册，回调地址：", bindUrl);
                        response.sendRedirect(bindUrl);
                        return;
                    }
                    sessionUser = new SessionUser(customer.getId(), customer.getUserNo(), customer.getUserName(), customer.getAlipayUserId());
                    request.getSession().setAttribute(campusProperties.getSessionUserKey(), sessionUser);
                    log.info("[访问认证]成功,支付宝验证成功");
                } catch (Exception e) {
                    throw new BusinessException("[访问认证]失败,支付宝获取登录信息失败");
                }
            }
            filterChain.doFilter(req, res);
        } catch (BusinessException be) {
            try {
                response.sendRedirect("");//跳转错误页面
            } catch (Exception e) {
            }
        } catch (Exception e) {
            log.error("[访问认证]失败", e);
            try {
                response.sendRedirect("");//跳转错误页面
            } catch (Exception e1) {
            }
        }
    }

}

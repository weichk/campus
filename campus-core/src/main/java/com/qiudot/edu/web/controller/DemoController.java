package com.qiudot.edu.web.controller;

import com.acooly.core.common.dao.support.PageInfo;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.qiudot.edu.config.CampusProperties;
import com.qiudot.edu.service.AliInterfaceService;
import com.qiudot.edu.service.TradeService;
import com.qiudot.edu.service.base.order.PageQueryTradeDetailsOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author qiubo
 */
@Slf4j
@Controller
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private AliInterfaceService aliInterfaceService;

    @Autowired
    private AlipayClient alipayClient;
    @Autowired
    private CampusProperties campusProperties;
    
    @Autowired
    private TradeService tradeService;

    @RequestMapping("/index")
    public String demo() {
        return "index";
    }

    @RequestMapping("/deductPay")
    public void testDeductPay(HttpServletRequest request, HttpServletResponse response) {
//        aliInterfaceService.deductPay();
    }

    @RequestMapping("/loginTest")
    public String loginTest(HttpServletRequest request, HttpServletResponse response){
    	log.info("===sessionUser:{}",request.getSession().getAttribute(campusProperties.getSessionUserKey()));
    	return "/demo/myTest";
    }
    
//    private String getLoginInfo(HttpServletRequest request){
//    	String alipayUserId = (String)request.getSession().getAttribute("alipayUserId");
//    	String authCode = request.getParameter("auth_code");
//    	if (Strings.isBlank(alipayUserId) && Strings.isBlank(authCode)){
//            return "redirect:" + campusProperties.getAlipayLoginUrl("http://47.100.217.13:8888/demo/loginTestGetUserId"); 
//    	}else if (Strings.isBlank(alipayUserId) && Strings.isNotBlank(authCode)){
//    		//3. 利用authCode获得authToken
//    		try {				
//    			AlipaySystemOauthTokenRequest oauthTokenRequest = new AlipaySystemOauthTokenRequest();
//    			oauthTokenRequest.setCode(authCode);
//    			oauthTokenRequest.setGrantType("authorization_code");
//    			AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(oauthTokenRequest);
//    			log.info("===alipayUserId:{}",oauthTokenResponse.getAlipayUserId());
//    			log.info("===userId:{}",oauthTokenResponse.getUserId());
//    			request.getSession().setAttribute("alipayUserId", oauthTokenResponse.getAlipayUserId());
//			} catch (Exception e) {
//				throw new BusinessException("支付宝获取登录信息失败");
//			}
//    	}else if (Strings.isNotBlank(alipayUserId)){
//    		
//    	}
//    }
    
    @RequestMapping("/loginTestGetUserId")
    public String loginTestGetUserId(HttpServletRequest request, HttpServletResponse response){
        //2. 获得authCode
        String authCode = request.getParameter("auth_code");
        try {
            //3. 利用authCode获得authToken
            AlipaySystemOauthTokenRequest oauthTokenRequest = new AlipaySystemOauthTokenRequest();
            oauthTokenRequest.setCode(authCode);
            oauthTokenRequest.setGrantType("authorization_code");
            AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient
                .execute(oauthTokenRequest);
            log.info("===alipayUserId:{}",oauthTokenResponse.getAlipayUserId());
            log.info("===userId:{}",oauthTokenResponse.getUserId());
            request.getSession().setAttribute("alipayUserId", oauthTokenResponse.getAlipayUserId());
            //成功获得authToken
            if (null != oauthTokenResponse && oauthTokenResponse.isSuccess()) {

                //4. 利用authToken获取用户信息
                AlipayUserInfoShareRequest userinfoShareRequest = new AlipayUserInfoShareRequest();
                AlipayUserInfoShareResponse userinfoShareResponse = alipayClient
                    .execute(userinfoShareRequest, oauthTokenResponse.getAccessToken());

                //成功获得用户信息
                if (null != userinfoShareResponse && userinfoShareResponse.isSuccess()) {
                    //这里仅是简单打印， 请开发者按实际情况自行进行处理
                	log.info("===获取用户信息成功：{}" + userinfoShareResponse.getBody());

                } else {
                    //这里仅是简单打印， 请开发者按实际情况自行进行处理
                    log.info("===获取用户信息失败");

                }
            } else {
                //这里仅是简单打印， 请开发者按实际情况自行进行处理
                log.info("===authCode换取authToken失败");
            }
        } catch (Exception e) {
            log.error("===异常",e);
        }
        return "/demo/myTest";
    }
    
    @RequestMapping("/testSession")
    public String testSession(HttpServletRequest request, HttpServletResponse response){
    	log.info("===testSession:{}",request.getSession().getAttribute("alipayUserId"));
    	return "/demo/myTest";
    }
    
    @RequestMapping("/queryTradeDetails")
    public void testQueryTradeDetails(HttpServletRequest request, HttpServletResponse response) {
        PageQueryTradeDetailsOrder order = new PageQueryTradeDetailsOrder();
        order.setPageInfo(new PageInfo());
        order.setUserNo("1234568");
        order.setCardNo("8888");
        order.setSchoolNo("3");
        order.setBeginDate("2018-08-01");
        order.setEndDate("2018-08-31");
        order.setBegintime("00:00");
        order.setEndtime("23:59");
        order.getPageInfo().setCurrentPage(0);
        order.getPageInfo().setCountOfCurrentPage(100);
        tradeService.pageQueryTradeOrderDetails(order);
    }
}

/*
 * www.prosysoft.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * shuijing 2018-10-16 13:56 创建
 */
package com.qiudot.edu.web.service;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.type.DBMap;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.core.utils.enums.Messageable;
import com.qiudot.edu.client.whdc.WebServiceClient;
import com.qiudot.edu.config.CampusProperties;
import com.qiudot.edu.entity.Customer;
import com.qiudot.edu.entity.CustomerCard;
import com.qiudot.edu.entity.Shool;
import com.qiudot.edu.enums.CardStatusEnum;
import com.qiudot.edu.security.SessionUser;
import com.qiudot.edu.service.CustomerCardService;
import com.qiudot.edu.service.CustomerService;
import com.qiudot.edu.service.ShoolService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author shuijing
 */
@Slf4j
@Controller
@RequestMapping(value = "/mobile/service/school")
public class SchoolController {
    @Autowired
    private ShoolService shoolService;
    @Autowired
    private CustomerCardService customerCardService;
    @Autowired
    private WebServiceClient webServiceClient;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CampusProperties campusProperties;

    @RequestMapping("list.html")
    public String list(HttpServletRequest request, Map<String, Object> model) {
        List<Shool> schools = shoolService.getAll();
        model.put("schools", schools);
        return "/mobile/service/list-school";
    }

    @RequestMapping("register.html")
    public String register(HttpServletRequest request, String shoolCode, Map<String, Object> model) {
        if (StringUtils.isEmpty(shoolCode)) {
            throw new BusinessException("学校编码为空");
        }
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(campusProperties.getSessionUserKey());
        if (sessionUser == null) {
            throw new BusinessException("用户未登录，请授权绑卡后重试");
        }
        String alipayUserId = sessionUser.getAlipayUserId();
        Customer customer = customerService.findByAlipayUserId(alipayUserId);
        if (customer == null) {
            throw new BusinessException("用户未授权获得个人信息，请授权后重试");
        }
        if (customer.getAgreementNo() == null) {
            log.info("{},{}签约号为空，跳转签约", sessionUser.getAlipayUserId(), sessionUser.getCustomerUserName());
            return "redirect:/alipay/userAgreementSign?schoolCode=" + shoolCode;
        }

        Shool school = shoolService.findBySchoolCode(shoolCode);
        model.put("school", school);
        return "/mobile/service/register";
    }

    /**
     * 用户绑卡
     *
     * @param cardNo
     * @param shoolCode
     * @param realName
     * @param certNo
     * @param mobileNo
     * @param request
     * @param httpServletResponse
     * @throws Exception
     */
    @RequestMapping("/customerCardBind")
    @ResponseBody
    public JsonResult customerCardBind(String cardNo, String shoolCode, String realName, String certNo, String mobileNo, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception {
        log.info("用户绑卡cardNo:{},shoolCode:{},realName:{},certNo:{},mobileNo:{}", cardNo, shoolCode, realName, certNo, mobileNo);

        JsonResult result = new JsonResult();

        try {
            Shool shool = shoolService.findBySchoolCode(shoolCode);
            if (shool == null) {
                throw new BusinessException("选择的学校不存在");
            }
            CustomerCard customerCd = customerCardService.findByCardNoAndSchoolCode(cardNo, shoolCode);
            if (customerCd != null) {
                throw new BusinessException("此卡已经存在，请换张卡后重试");
            }

            SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(campusProperties.getSessionUserKey());
            if (sessionUser == null) {
                throw new BusinessException("用户登录，请授权绑卡后重试");
            }

            String aliPayUserId = sessionUser.getAlipayUserId();

            CustomerCard ccd = customerCardService.findByCardNoAndSchoolCodeAndAlipayId(cardNo, shoolCode, aliPayUserId);
            if (ccd != null) {
                throw new BusinessException("一个用户只能绑定一张卡，请换张卡后重试");
            }

            Customer customer = customerService.findByAlipayUserId(aliPayUserId);
            if (customer == null) {
                throw new BusinessException("用户还未绑定，请授权绑卡后重试");
            } else {
                if (StringUtils.isEmpty(customer.getAgreementNo())) {
                    throw new BusinessException("用户还未代扣签约");
                }
            }
            DBMap extJson = customer.getExtJson();
            if (extJson == null) {
                throw new BusinessException("用户信息不存在");
            }

            //验证一卡通账号是否正确
            //0 = {HashMap$Node@11308} "number" -> "8888"
            //1 = {HashMap$Node@11309} "balance" -> "825838"
            //2 = {HashMap$Node@11310} "identity" -> "0"
            //3 = {HashMap$Node@11311} "name" -> "李红梅"
            //4 = {HashMap$Node@11312} "balancetime" -> "2018-10-11 15:57:16"
            //5 = {HashMap$Node@11313} "status" -> "正常"
            //mock 五和大成
            if (!campusProperties.isMockWHDCApi()) {
                Map<String, String> mapr = webServiceClient.getPersonInfo(shoolCode, cardNo);
                if (mapr == null) {
                    throw new BusinessException("五和大成卡号验证失败");
                }

                String status = mapr.get("status");
                if (!"正常".equals(status)) {
                    throw new BusinessException("一卡通帐号不正常");
                }
                if (!realName.equals(mapr.get("name"))) {
                    throw new BusinessException("用户名不正确");

                }
                //调用一卡通绑定支付宝授权信息
                String avatar = (String) extJson.get("avatar");
                //{"user_status":"T","user_type":"2","province":"重庆","gender":"m","city":"重庆市","is_certified":"T","nick_name":"xx","avatar":"https://tfs.alipayobjects.com/images/partner/T1d84uXbVeXXXXXXXX","is_student_certified":"F"}
                webServiceClient.ZFB_BindDoc(shoolCode, cardNo, realName, mobileNo, "123456",
                        certNo, aliPayUserId, avatar, (String) extJson.get("province"), (String) extJson.get("city"), (String) extJson.get("nick_name"), (String) extJson.get("is_student_certified"), (String) extJson.get("user_type"), (String) extJson.get("user_status"), (String) extJson.get("is_certified"), (String) extJson.get("gender"));
            }

            CustomerCard customerCard = new CustomerCard();
            customerCard.setAlipayUserId(aliPayUserId);
            customerCard.setCardNo(cardNo);
            customerCard.setRealName(realName);
            customerCard.setMobileNo(mobileNo);
            customerCard.setCertNo(certNo);
            customerCard.setCardStatus(CardStatusEnum.ENABLE);
            customerCard.setShoolCode(shoolCode);
            customerCard.setCustomerUserNo(customer.getUserNo());
            customerCard.setShoolName(shool.getShoolName());
            customerCardService.save(customerCard);
        } catch (Exception e) {
            String message = e.getMessage();
            log.error(message, e);
            result.setSuccess(false);
            if (e instanceof BusinessException) {
                Messageable be = (Messageable) e;
                result.setCode(be.code());
                result.setMessage(be.message());
            } else {
                result.setCode(e.getClass().toString());
                result.setMessage("内部异常");
            }
        }

        log.info("用户绑卡成功cardNo:{},shoolCode:{},realName:{},certNo:{},mobileNo:{}", cardNo, shoolCode, realName, certNo, mobileNo);

        return result;
    }

}

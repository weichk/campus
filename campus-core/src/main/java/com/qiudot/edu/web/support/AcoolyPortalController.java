package com.qiudot.edu.web.support;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.common.web.support.JsonListResult;
import com.acooly.core.utils.Servlets;
import com.acooly.core.utils.Strings;
import com.acooly.core.utils.enums.Messageable;
import com.acooly.openapi.framework.common.message.PageApiRequest;
import com.acooly.openapi.framework.common.message.PageApiResponse;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author zhangpu@acooly.cn
 * @date 2018-08-18 14:22
 */
@Slf4j
public class AcoolyPortalController extends AbstractJQueryEntityController {

    protected static int WAN_YUAN_UNIT = 10000;

    protected String commonResultView = "redirect:/mobile/common/result.html";

    {
        super.allowMapping = "*";
    }

//    protected String getParameter(HttpServletRequest request, String name) {
//        String value = Servlets.getParameter(request, name);
//        if (Strings.isBlank(value)) {
//            Map<String, String> requestData = getRequestData(request);
//            if (requestData != null) {
//                value = requestData.get(name);
//            }
//        }
//        if (Strings.isBlank(value)) {
//            SessionUser sessionUser = getSessionUser(request);
//            if (sessionUser != null) {
//                if (Strings.equals("userId", name)) {
//                    value = String.valueOf(sessionUser.getUserId());
//                } else if (Strings.equals("userNo", name)) {
//                    value = sessionUser.getUserNo();
//                } else if (Strings.equals("userName", name)) {
//                    value = sessionUser.getUserName();
//                } else if (Strings.equals("realName", name)) {
//                    value = sessionUser.getRealName();
//                }
//            }
//        }
//        return value;
//    }

//    protected String getUserNo(HttpServletRequest request) {
//        String userNo = getParameter(request, "userNo");
//
//        Assert.hasLength(userNo, "会员编码不能为空");
//        return userNo;
//    }

//    protected String getUserName(HttpServletRequest request) {
//        return getParameter(request, "userName");
//    }
//
//    protected String getRealName(HttpServletRequest request) {
//        return getParameter(request, "realName");
//    }
//
//    protected Long getUserId(HttpServletRequest request) {
//        String userId = getParameter(request, "userId");
//        if (Strings.isNumeric(userId)) {
//            return Long.valueOf(userId);
//        }
//        return null;
//    }
//
//    protected String deliverUserNo(HttpServletRequest request) {
//        String userNo = getUserNo(request);
//        request.setAttribute("userNo", userNo);
//        return userNo;
//    }
//
//    protected String deliverAssetNo(HttpServletRequest request) {
//        String assetNo = getParameter(request, "assetNo");
//        Assert.hasLength(assetNo, "资产编号不能为空");
//        request.setAttribute("assetNo", assetNo);
//        return assetNo;
//    }


    protected void fillPageApiRequest(PageApiRequest request, PageInfo pageInfo) {
        request.setLimit(pageInfo.getCountOfCurrentPage());
        request.setStart(pageInfo.getCurrentPage());
    }

    protected void fillJsonListResult(PageApiResponse response, JsonListResult result, PageInfo pageInfo) {
        result.setRows(response.getRows());
        result.setPageSize(pageInfo.getCountOfCurrentPage());
        result.setPageNo(pageInfo.getCurrentPage());
        result.setHasNext(pageInfo.getCurrentPage() < response.getTotalPages());
    }

    protected void fillJsonListResultWithPageInfo(JsonListResult result, PageInfo pageInfo) {
        result.setRows(pageInfo.getPageResults());
        result.setPageSize(pageInfo.getCountOfCurrentPage());
        result.setPageNo(pageInfo.getCurrentPage());
        result.setHasNext(pageInfo.getCurrentPage() < pageInfo.getTotalPage());
    }

    @Override
    protected void handleException(String action, Exception e, HttpServletRequest request) {
        String message;
        if (e instanceof Messageable) {
            Messageable be = (Messageable) e;
            message = be.message();
        } else {
            message = getExceptionMessage(action, e);
        }
        log.error(message);
        this.saveMessage(request, message);
    }

    protected String handleException(HttpServletRequest request, Exception e) {
        handleException(null, e, request);
        return this.commonResultView;
    }

    @Override
    protected void saveMessage(HttpServletRequest request, String message) {
        if (StringUtils.isNotBlank(message)) {
            request.getSession().setAttribute("messages", Lists.newArrayList(message));
        }
    }

    /**
     * 从body取参数
     * @param request
     * @param key
     * @return
     */
    protected String getByNameForNotService(HttpServletRequest request, String key){
        String value = Servlets.getParameter(request, key);
        if (Strings.isBlank(value)){
            String body = Servlets.getParameter(request, "body");

            Map<String, String> data = JSON.parseObject(body, Map.class);
            if (data != null){
                value = data.get(key);
            }
        }
        return value;
    }

    /**
     * 从body获取参数
     * @param request
     * @param key
     * @return
     */
    protected String getByNameFromBody(HttpServletRequest request, String key){
        String value = getByNameForNotService(request, key);
        request.setAttribute(key, value);
        return value;
    }
}

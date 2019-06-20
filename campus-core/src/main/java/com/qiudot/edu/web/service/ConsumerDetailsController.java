package com.qiudot.edu.web.service;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.web.support.JsonEntityResult;
import com.acooly.core.common.web.support.JsonListResult;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.core.utils.Money;
import com.acooly.core.utils.Servlets;
import com.acooly.openapi.framework.common.utils.Dates;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.qiudot.edu.config.CampusProperties;
import com.qiudot.edu.entity.CustomerCard;
import com.qiudot.edu.security.SessionUser;
import com.qiudot.edu.service.CustomerCardService;
import com.qiudot.edu.service.TradeService;
import com.qiudot.edu.service.base.dto.QueryTradeOrderDetailDto;
import com.qiudot.edu.service.base.order.PageQueryTradeDetailsOrder;
import com.qiudot.edu.service.base.result.PageQueryTradeOrderDetailsResult;
import com.qiudot.edu.web.support.AcoolyPortalController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author zhike@acooly.com
 * @date 2018-10-15 17:54
 */
@Slf4j
@Controller
@RequestMapping("/mobile/service/consumer")
public class ConsumerDetailsController extends AcoolyPortalController {

    @Autowired
    private TradeService tradeService;

    @Autowired
    private CampusProperties campusProperties;

    @Autowired
    private CustomerCardService customerCardService;

    /**
     * 获取用户卡列表
     */
    @RequestMapping(path = "checkBindCard", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult checkBindCard(HttpServletRequest request, HttpServletResponse response, Model model) {
        JsonResult result = new JsonResult();
        SessionUser sessionUser = (SessionUser)request.getSession().getAttribute(campusProperties.getSessionUserKey());
        String userNo = sessionUser.getCustomerUserNo();
        CustomerCard customerCard = customerCardService.getLastBindCardByUserNo(userNo);
        if(customerCard == null) {
           result.setSuccess(false);
           result.setMessage("还未绑卡");
        }
        return result;
    }
    /**
     * 消费明细 视图
     */
    @RequestMapping(path = "consumerDetails", method = RequestMethod.GET)
    public String consumerDetailsView(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("nowDate",Dates.getYear(new Date())+" 年 " +Dates.getMonth(new Date())+" 月");
        return "/mobile/service/consumerDetails";
    }

    /**
     * 获取用户卡列表
     */
    @RequestMapping(path = "getUserCardList", method = RequestMethod.POST)
    @ResponseBody
    public JsonListResult<CustomerCard> getUserCardList(HttpServletRequest request, HttpServletResponse response, Model model) {
        JsonListResult<CustomerCard> result = new JsonListResult<>();
        SessionUser sessionUser = (SessionUser)request.getSession().getAttribute(campusProperties.getSessionUserKey());
        log.info("获取过滤器sessionUser={}", JSON.toJSONString(sessionUser));
        String userNo = sessionUser.getCustomerUserNo();
        List<CustomerCard> customerCards = customerCardService.getCardListByUserNo(userNo);
        result.setSuccess(true);
        result.setRows(customerCards);
        return result;
    }

    /**
     * 消费明细 数据
     */
    @RequestMapping(path = "consumerDetails", method = RequestMethod.POST)
    @ResponseBody
    public JsonListResult<QueryTradeOrderDetailDto> consumerDetails(HttpServletRequest request, HttpServletResponse response, Model model) {
        String cardNo = Servlets.getParameter(request,"cardNo");
        String schoolNo = Servlets.getParameter(request,"schoolNo");
        String queryDate = Servlets.getParameter(request,"queryDate");
        int page = Servlets.getIntParameter(request,"page")-1;
        int rows = Servlets.getIntParameter(request,"rows");
        String beginDate = Dates.format(Dates.getFirstDateOfMonth(Dates.parse(queryDate,"yyyy-MM")),Dates.CHINESE_DATE_FORMAT_LINE);
        String endDate = Dates.format(Dates.getLastDateOfMonth(Dates.parse(queryDate,"yyyy-MM")),Dates.CHINESE_DATE_FORMAT_LINE);
        PageQueryTradeDetailsOrder order = new PageQueryTradeDetailsOrder();
        order.setPageInfo(new PageInfo());
        SessionUser sessionUser = (SessionUser)request.getSession().getAttribute(campusProperties.getSessionUserKey());
        order.setUserNo(sessionUser.getCustomerUserNo());
        order.setCardNo(cardNo);
        order.setSchoolNo(schoolNo);
        order.setBeginDate(beginDate);
        order.setEndDate(endDate);
        order.setBegintime("00:00");
        order.setEndtime("23:59");

        order.getPageInfo().setCurrentPage(page);
        order.getPageInfo().setCountOfCurrentPage(rows);
        PageQueryTradeOrderDetailsResult tradeOrderDetailsResult = tradeService.pageQueryTradeOrderDetails(order);
        JsonListResult<QueryTradeOrderDetailDto> result = new JsonListResult<>();
        result.setRows(tradeOrderDetailsResult.getDto().getPageResults());
        result.setPageSize(tradeOrderDetailsResult.getDto().getCountOfCurrentPage());
        result.setPageNo(tradeOrderDetailsResult.getDto().getCurrentPage());
        result.setHasNext(tradeOrderDetailsResult.getDto().getCurrentPage() < tradeOrderDetailsResult.getDto().getTotalPage());
        //MOCK
        mockData(result);
        return result;
    }


    private void mockData(JsonListResult<QueryTradeOrderDetailDto> result) {
        List<QueryTradeOrderDetailDto> queryTradeOrderDetailDtos = Lists.newArrayList();
        for(int i=0;i<10;i++) {
            QueryTradeOrderDetailDto queryTradeOrderDetailDto = new QueryTradeOrderDetailDto();
            queryTradeOrderDetailDto.setBalance(Money.amout("3221.22"));
            queryTradeOrderDetailDto.setAmount(Money.amout("10"));
            queryTradeOrderDetailDto.setTradeTime("2018-09-10 12:22");
            queryTradeOrderDetailDto.setTradeTitle("一食堂"+i);
            queryTradeOrderDetailDtos.add(queryTradeOrderDetailDto);
        }
        result.setRows(queryTradeOrderDetailDtos);
        result.setPageSize(10);
        result.setPageNo(0);
        result.setHasNext(true);
    }
    /**
     * 获取用户卡信息
     */
    @RequestMapping(path = "getUserCardInfo", method = RequestMethod.POST)
    @ResponseBody
    public JsonEntityResult<CustomerCard> getUserCardInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
        JsonEntityResult<CustomerCard> result = new JsonEntityResult<>();
        SessionUser sessionUser = (SessionUser)request.getSession().getAttribute(campusProperties.getSessionUserKey());
        long cardId = Servlets.getLongParameter("id");
        CustomerCard customerCard = null;
        if(cardId == 0) {
            String userNo = sessionUser.getCustomerUserNo();
            customerCard = customerCardService.getLastBindCardByUserNo(userNo);
        } else {
            customerCard = customerCardService.get(cardId);
        }
        result.setEntity(customerCard);
        result.setSuccess(true);
        return result;
    }

}

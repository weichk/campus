package com.qiudot.edu.service.impl;

import com.acooly.core.utils.Dates;
import com.acooly.core.utils.Money;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.qiudot.edu.client.whdc.WebServiceClient;
import com.qiudot.edu.service.WhdcInterfaceService;
import com.qiudot.edu.service.base.dto.QueryTradeOrderDetailDto;
import com.qiudot.edu.service.base.dto.WhdcTradeItemsDto;
import com.qiudot.edu.service.base.order.WhdcGetPersonDetailsOrder;
import com.qiudot.edu.service.base.result.PageQueryTradeOrderDetailsResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zhike@acooly.com
 * @date 2018-10-10 17:59
 */
@Service
@Slf4j
public class WhdcInterfaceServiceImpl implements WhdcInterfaceService {

    @Autowired
    private WebServiceClient webServiceClient;

    @Override
    public PageQueryTradeOrderDetailsResult queryTradeOrderDetails(WhdcGetPersonDetailsOrder whdcGetPersonDetailsOrder) {
        PageQueryTradeOrderDetailsResult result = new PageQueryTradeOrderDetailsResult();
        result.setDto(whdcGetPersonDetailsOrder.getPageInfo());
        String startTime = whdcGetPersonDetailsOrder.getBegintime();
        String endTime = whdcGetPersonDetailsOrder.getEndtime();
        String userCode = whdcGetPersonDetailsOrder.getUsercode();
        String personNo = whdcGetPersonDetailsOrder.getPersonno();
        String beginDate = whdcGetPersonDetailsOrder.getBeginDate();
        String endDate = whdcGetPersonDetailsOrder.getEndDate();
        String pageIndex = whdcGetPersonDetailsOrder.getPageInfo().getCurrentPage() + "";
        String itemMaxCount = whdcGetPersonDetailsOrder.getPageInfo().getCountOfCurrentPage() + "";
        try {
            Map<String, Object> resultMap = webServiceClient.GetPersonDetails(userCode, personNo, beginDate, endDate, startTime, endTime, pageIndex, itemMaxCount);
            if (resultMap != null) {
                long totalCount = Long.valueOf(resultMap.get("totalitemcount").toString());
                long totalPage = Long.valueOf(resultMap.get("pagecount").toString());
                result.getDto().setTotalCount(totalCount);
                result.getDto().setTotalPage(totalPage);
                String tradeDetails = resultMap.get("items").toString();
                List<WhdcTradeItemsDto> whdcTradeItemsDtos = JSONObject.parseArray(tradeDetails, WhdcTradeItemsDto.class);
                List<QueryTradeOrderDetailDto> queryTradeOrderDetailDtos = Lists.newArrayList();
                whdcTradeItemsDtos.forEach(whdcTradeItemsDto -> {
                    QueryTradeOrderDetailDto queryTradeOrderDetailDto = new QueryTradeOrderDetailDto();
                    queryTradeOrderDetailDto.setTradeTitle(whdcTradeItemsDto.getOperater());
                    queryTradeOrderDetailDto.setTradeTime(whdcTradeItemsDto.getCdate() + " " + whdcTradeItemsDto.getCtime());
                    queryTradeOrderDetailDto.setAmount(Money.cent(Long.valueOf(whdcTradeItemsDto.getMoney())));
                    queryTradeOrderDetailDto.setBalance(Money.cent(Long.valueOf(whdcTradeItemsDto.getBalance())));
                    queryTradeOrderDetailDtos.add(queryTradeOrderDetailDto);
                });
                result.getDto().setPageResults(queryTradeOrderDetailDtos);
            }
        } catch (Exception e) {
            log.info("用户personno={}查询交易流水失败：{}", personNo, e.getMessage());
        }
        return result;
    }
}

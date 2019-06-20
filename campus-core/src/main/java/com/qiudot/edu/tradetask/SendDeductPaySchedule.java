package com.qiudot.edu.tradetask;

import com.qiudot.edu.service.TradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 定时发送到网关进行代扣定时任务
 *
 * @author zhike 2018/8/15 17:30
 */
@Slf4j
@Service("sendDeductPaySchedule")
public class SendDeductPaySchedule {

    @Autowired
    private TradeService tradeService;

    /**
     * 定时发送到网关进行代扣定时任务
     */
    public void justDoIT() {
        tradeService.querySendDeductPay();
    }
}

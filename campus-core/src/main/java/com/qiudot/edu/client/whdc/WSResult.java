package com.qiudot.edu.client.whdc;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.facade.InfoBase;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author qiuboboy@qq.com
 * @date 2018-09-28 14:11
 */
@Slf4j
@Getter
@Setter
public class WSResult extends InfoBase {
    private String responsecode;
    private String responseinfo;
    private Map<String, String> result;

    public void throwIfFailure() {
        if (!"0000".equals(responsecode)) {
            throw new BusinessException(responseinfo,responsecode,false);
        }
    }
}

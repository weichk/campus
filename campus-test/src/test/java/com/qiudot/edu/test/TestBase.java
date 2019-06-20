
package com.qiudot.edu.test;
import com.acooly.core.common.boot.Apps;
import com.acooly.module.test.AppWebTestBase;


/**
 * 测试父类
 *
 * @author qiubo
 */
public abstract class TestBase extends AppWebTestBase {
    protected static final String PROFILE = "net";

    static {
        Apps.setProfileIfNotExists(PROFILE);
    }

}

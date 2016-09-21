package com.mobaopay.test.feature;

import com.ruby.wechat.Constants;
import com.mobaopay.test.BaseSpringTestRunner;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static junit.framework.TestCase.*;

/**
 * Created by ruby on 2016/5/24.
 * Mail:liyufeng_23@163.com
 */
public class PropertiesTest extends BaseSpringTestRunner{
    private static final Logger logger = LoggerFactory.getLogger(PropertiesTest.class);

    @Test
    public void testReadProperties() {
        logger.info("find properties in wpms");
        assertEquals("S7BCD4fFVrX5IL9v", Constants.WX_TOKEN);
    }
}

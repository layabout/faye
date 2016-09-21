package com.mobaopay.test.feature;

import com.mobaopay.test.BaseSpringTestRunner;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.annotation.Resource;

import static junit.framework.TestCase.assertNotNull;

/**
 * Created by ruby on 16/5/23.
 * Email:liyufeng_23@163.com
 */
public class ThymeleafTest extends BaseSpringTestRunner {

    private static final Logger logger = LoggerFactory.getLogger(ThymeleafTest.class);

    @Resource
    private TemplateEngine xmlTemplateEngine;

    /**
     * 测试使用Thymeleaf解析xml文档
     */
    @Test
    public void testParseXml(){

        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("messages/request/");
        resolver.setSuffix(".xml");
        resolver.setTemplateMode("XML");

        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);

        Context context = new Context();
        context.setVariable("dictTypeId","11111");
        String result = engine.process("34001002",context);
        logger.info(result);

        context.setVariable("dictTypeId","22222");
        result = xmlTemplateEngine.process("34001002",context);
        logger.info(result);

        assertNotNull(result);
    }
}

package com.mobaopay.test.feature;

import com.mobaopay.test.BaseSpringTestRunner;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.IOException;

/**
 * Created by ruby on 2016/5/24.
 * Mail:liyufeng_23@163.com
 */
public class HttpClientTest extends BaseSpringTestRunner{
    private static final Logger logger = LoggerFactory.getLogger(HttpClientTest.class);

    @Test
    public void testHttpRequest() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        try {
            HttpPost post = new HttpPost("http://mgr.mobaopay.dev/customerServiceServlet");

            ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
            resolver.setPrefix("messages/request/");
            resolver.setSuffix(".xml");
            resolver.setTemplateMode("XML");

            TemplateEngine engine = new TemplateEngine();
            engine.setTemplateResolver(resolver);

            Context context = new Context();
            context.setVariable("dictTypeId","5");
            String result = engine.process("34001002",context);
            logger.info("请求数据: " + result);

            Base64 base64 = new Base64();
            byte[] b = result.getBytes("utf-8");
            b = base64.encode(b);
            result = new String(b);
            logger.info("编码请求: " + result);

            StringEntity entity = new StringEntity(result,"UTF-8");
            post.setEntity(entity);
            CloseableHttpResponse response = client.execute(post);

            String bodyAsString = EntityUtils.toString(response.getEntity());
            logger.info(bodyAsString);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
    }
}

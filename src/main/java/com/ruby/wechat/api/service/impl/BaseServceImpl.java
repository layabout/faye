package com.ruby.wechat.api.service.impl;

import com.ruby.wechat.api.service.BaseService;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.TemplateEngine;

import java.io.IOException;

/**
 * Created by ruby on 2016/9/30.
 * Email:liyufeng_23@163.com
 */
public class BaseServceImpl implements BaseService {

    private static final Logger logger = LoggerFactory.getLogger(BaseServceImpl.class);

    @Autowired
    protected TemplateEngine xmlTemplateEngine;


    @Override
    public String sendPostToMgrService(String data) throws Exception {
        return sendPost(data, "mgr_url");
    }

    @Override
    public String sendPost(String data, String url) throws Exception {

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        CloseableHttpResponse response = null;

        logger.trace("请求数据: {}", data);
        StringEntity entity = new StringEntity(data, "UTF-8");
        post.setEntity(entity);

        try {
            response = client.execute(post);
            String bodyAsString = EntityUtils.toString(response.getEntity());
            logger.trace("返回结果: {}", bodyAsString);

            return bodyAsString;

        } catch (IOException e) {

            throw new Exception("Post请求失败");

        } finally {

            client.close();

        }

    }
}

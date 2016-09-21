package com.ruby.wechat.api.manager;

import com.ruby.wechat.Constants;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by ruby on 2016/9/21.
 * Email:liyufeng_23@163.com
 */
public class AccessTokenManager {

    /**
     * 向微服务器请求accessToken
     * @param appid 第三方用户唯一凭证
     * @param appsecret 第三方用户唯一凭证密钥
     * @return
     */
    public static String askAccessToken(String appid, String appsecret) {

        CloseableHttpClient client = HttpClients.createDefault();

        String requestUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid +"&secret=" + appsecret;
        HttpGet get = new HttpGet(requestUrl);

        try {

            CloseableHttpResponse response = client.execute(get);
            String bodyAsString = EntityUtils.toString(response.getEntity());
            System.out.println(bodyAsString);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        askAccessToken(Constants.WX_APPID, Constants.WX_APPSECRET);
    }
}

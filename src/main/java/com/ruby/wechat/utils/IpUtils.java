package com.ruby.wechat.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;

public class IpUtils {

    private static String WECHAT_SERVER_IPS = null;

    /**
     * 获取微信服务器IP列表
     * @param accessToken
     * @return
     * @throws Exception
     */
    public static String getWechatServerIps(String accessToken) throws Exception{
        if (WECHAT_SERVER_IPS == null) {
            CloseableHttpClient client = HttpClients.createDefault();
            String url = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=" + accessToken;
            HttpGet get = new HttpGet(url);
            CloseableHttpResponse response = client.execute(get);
            String ips = EntityUtils.toString(response.getEntity());

            setWechatServerIps(ips);
        }
        return WECHAT_SERVER_IPS;
    }

    public static void setWechatServerIps(String wechatServerIps) {
        WECHAT_SERVER_IPS = wechatServerIps;
    }

    /**
     * 验证请求是否来自微信服务器
     * @param requestIP
     * @param wechatIPList
     * @return
     */
    public static boolean validateRequestFromWXServers(String requestIP, String wechatIPList) {
        return wechatIPList.contains(requestIP);
    }

    /**
     * 判断请求IP是否在白名单中
     * @param request
     * @param whiteList
     * @return
     */
    public static boolean isIpAllowed(HttpServletRequest request, String whiteList) {

        boolean allowed = false;
        String[] allowed_ips = whiteList.trim().split("\\|");

        for (int i =0; i<allowed_ips.length; i++) {
            if (allowed_ips[i].equals(request.getRemoteAddr().trim())) {
                allowed = true;
                break;
            }
        }

        return allowed;
    }
}

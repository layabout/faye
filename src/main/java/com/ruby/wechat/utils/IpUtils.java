package com.ruby.wechat.utils;

import javax.servlet.http.HttpServletRequest;

public class IpUtils {

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

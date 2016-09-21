package com.ruby.wechat.web.support.util;

/**
 * Created by ruby on 2016/5/26.
 * Email:liyufeng_23@163.com
 */
public class AjaxUtils {

    private AjaxUtils() {

    }

    public static boolean isAjaxRequest(String requestedWith) {
        return requestedWith != null ? "XMLHttpRequest".equals(requestedWith) : false;
    }
}

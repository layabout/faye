package com.ruby.common.util;

/**
 * Created by ruby on 2016/6/3.
 * Email:liyufeng_23@163.com
 */
public class SensitiveDataProtectUtil {

    public final static String protectCardNo(String origin) {
        StringBuilder sb = new StringBuilder(origin);
        String protection = sb.replace(3,15,"************").toString();
        return protection;
    }
}

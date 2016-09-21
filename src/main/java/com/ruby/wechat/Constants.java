package com.ruby.wechat;

import com.ruby.common.configuration.ConfigurableConstants;

public class Constants extends ConfigurableConstants{

    static {
        init("config/wpms.properties");
    }

    //token
    public final static String WX_TOKEN = p.getProperty("wx_token");

    //appID
    public final static String WX_APPID = p.getProperty("wx_appId");

    //appsecret
    public final static String WX_APPSECRET = p.getProperty("wx_appsecret");

    //aesKey
    public final static String WX_ENCODING_AESKEY = p.getProperty("wx_encodingAesKey");

}

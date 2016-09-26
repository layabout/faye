package com.ruby.wechat.api.service;

/**
 * Created by ruby on 2016/9/26.
 * Email:liyufeng_23@163.com
 */
public interface WXAutoReplyService {

    /**
     * 获取自动回复消息
     * @param receiveXml 请求报文
     * @return
     */
    String getAutoReplyMessage(String receiveXml) throws Exception;
}

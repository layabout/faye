package com.ruby.wechat.api.service;

/**
 * Created by ruby on 2016/9/26.
 * Email:liyufeng_23@163.com
 */
public interface WXBusinessProcessService {

    /**
     * 回复明文消息
     * @param receiveXml 接收到的消息报文
     * @return
     */
    String replyMessage(String receiveXml) throws Exception;

    /**
     * 回复加密消息
     * @param receiveXml
     * @return
     */
    String replyEncryptedMessage(String receiveXml) throws Exception;

    /**
     * 发送模板消息
     * @param templateId 模板ID
     * @return
     */
    String sendTemplateMessage(String templateId) throws Exception;
}

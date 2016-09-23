package com.ruby.wechat.api.manager;

import com.ruby.wechat.utils.WXBizMsgCrypt;
import com.ruby.wechat.utils.WXUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ruby on 2016/9/23.
 * Email:liyufeng_23@163.com
 */
public class WXMessageManager {

    private static final Logger logger = LoggerFactory.getLogger(WXMessageManager.class);

    /**
     * 回复文本消息
     * @param toUserName
     * @param fromUserName
     * @param content
     * @return
     */
    public static String replayTextMessage(String toUserName, String fromUserName, String content) throws Exception {

        String format = "<xml><ToUserName><![CDATA[%1$s]]></ToUserName><FromUserName><![CDATA[%2$s]]></FromUserName><CreateTime>%3$s</CreateTime><MsgType><![CDATA[%4$s]]></MsgType><Content><![CDATA[%5$s]]></Content></xml>";

        String timestamp = String.valueOf(System.currentTimeMillis());
        String nonce = WXUtil.getRandomNum(9);

        String replayXML = String.format(format, toUserName, fromUserName, timestamp, "text", content);

        WXBizMsgCrypt msgCrypt = WXUtil.getWxBizMsgCryptInstance();
        String decryptXML = msgCrypt.encryptMsg(replayXML, timestamp, nonce);

        logger.trace("加密回复消息: {}", decryptXML);

        return decryptXML;
    }

}

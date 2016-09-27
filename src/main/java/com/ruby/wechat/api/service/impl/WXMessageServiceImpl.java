package com.ruby.wechat.api.service.impl;

import com.ruby.wechat.api.dto.WXReceiveText;
import com.ruby.wechat.api.dto.WXSubscribeEvent;
import com.ruby.wechat.api.service.WXMessageService;
import com.ruby.wechat.utils.ReceiveType;
import com.ruby.wechat.utils.WXBizMsgCrypt;
import com.ruby.wechat.utils.WXUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by ruby on 2016/9/26.
 * Email:liyufeng_23@163.com
 */
@Service
public class WXMessageServiceImpl implements WXMessageService {

    private static final Logger logger = LoggerFactory.getLogger(WXMessageServiceImpl.class);

    //加密模式
    public static String DECRYPT_TYPE = "aes";

    //接收方
    private static String TO_USER_NAME;

    //发送方
    private static String FROM_USER_NAME;

    @Override
    public String replyTextMessage(String content) throws Exception {

        String format = "<xml><ToUserName><![CDATA[%1$s]]></ToUserName><FromUserName><![CDATA[%2$s]]></FromUserName><CreateTime>%3$s</CreateTime><MsgType><![CDATA[%4$s]]></MsgType><Content><![CDATA[%5$s]]></Content></xml>";

        // 生成时间戳
        String timestamp = String.valueOf(System.currentTimeMillis());

        String replyXml = String.format(format, TO_USER_NAME, FROM_USER_NAME, timestamp, "text", content);

        if (DECRYPT_TYPE.equals("aes")) {

            //生成随机数
            String nonce = WXUtil.getRandomNum(9);
            WXBizMsgCrypt msgCrypt = WXUtil.getWxBizMsgCryptInstance();
            replyXml = msgCrypt.encryptMsg(replyXml, timestamp, nonce);

        }

        logger.trace("回复消息: {}", replyXml);

        return replyXml;
    }

    @Override
    public String processor(String receiveXml) throws Exception {

        // 获取消息类型
        String msgType = WXUtil.getTagValue(receiveXml, "MsgType");

        // 设置消息接发方
        TO_USER_NAME = WXUtil.getTagValue(receiveXml, "FromUserName");

        FROM_USER_NAME = WXUtil.getTagValue(receiveXml, "ToUserName");

        if (msgType.equals(ReceiveType.TEXT.toString())) {

            logger.trace("接收文本消息");

            WXReceiveText message = WXUtil.convertToBean(receiveXml, WXReceiveText.class);

            String  content = "";

            if (message.getContent().contains("你好")) content = "您好！";

            return replyTextMessage(content);

        } else if(msgType.equals(ReceiveType.EVENT.toString())) {

            logger.trace("接收事件推送");

            // 获取事件类型
            String event = WXUtil.getTagValue(receiveXml, "Event");
            // 订阅、取消订阅事件
            if (event.equals(WXSubscribeEvent.SUBSCRIBE) || event.equals(WXSubscribeEvent.UNSUBSCRIBE)) {

                WXSubscribeEvent subscribeEvent = WXUtil.convertToBean(receiveXml, WXSubscribeEvent.class);

                // 订阅事件
                if (subscribeEvent.getEvent().equals(WXSubscribeEvent.SUBSCRIBE)) {

                    logger.trace("订阅事件");
                    String  content = "您好！欢迎关注Mo宝支付！客服热线400-677-1357";

                    return replyTextMessage(content);

                }
            }

        }
        return "success";
    }
}

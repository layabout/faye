package com.ruby.wechat.api.service.impl;

import com.ruby.wechat.api.dto.WXReceiveText;
import com.ruby.wechat.api.dto.WXSubscribeEvent;
import com.ruby.wechat.api.dto.WXTemplateSendJobFinishEvent;
import com.ruby.wechat.api.service.WXMessageService;
import com.ruby.wechat.utils.ReceiveType;
import com.ruby.wechat.utils.WXBizMsgCrypt;
import com.ruby.wechat.utils.WXUtils;
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
            String nonce = WXUtils.getRandomNum(9);
            WXBizMsgCrypt msgCrypt = WXUtils.getWxBizMsgCryptInstance();
            replyXml = msgCrypt.encryptMsg(replyXml, timestamp, nonce);

        }

        logger.trace("回复消息: {}", replyXml);

        return replyXml;
    }

    @Override
    public String processor(String receiveXml) throws Exception {

        // 获取消息类型
        String msgType = WXUtils.getTagValue(receiveXml, "MsgType");

        // 设置消息接发方
        TO_USER_NAME = WXUtils.getTagValue(receiveXml, "FromUserName");

        FROM_USER_NAME = WXUtils.getTagValue(receiveXml, "ToUserName");

        if (msgType.equals(ReceiveType.TEXT.toString())) {

            logger.trace("接收文本消息");

            WXReceiveText message = WXUtils.convertToBean(receiveXml, WXReceiveText.class);

            String  content = "";

            if (message.getContent().contains("你好"))
                content = "您好！";
            else {
                content = "您好！您可以通过以下方式同我们联系：\n" +
                        "【1】登录我们的官方网站：www.mobaopay.com\n" +
                        "【2】选择拨打客服热线：4006771357\n" +
                        "【3】选择登录在线客服：（QQ）4006771357";
            }


            return replyTextMessage(content);

        } else if(msgType.equals(ReceiveType.EVENT.toString())) {

            logger.trace("接收事件推送");

            // 获取事件类型
            String event = WXUtils.getTagValue(receiveXml, "Event");
            // 订阅、取消订阅事件
            if (event.equals(WXSubscribeEvent.SUBSCRIBE) || event.equals(WXSubscribeEvent.UNSUBSCRIBE)) {

                WXSubscribeEvent subscribeEvent = WXUtils.convertToBean(receiveXml, WXSubscribeEvent.class);

                // 订阅事件
                if (subscribeEvent.getEvent().equals(WXSubscribeEvent.SUBSCRIBE)) {

                    logger.trace("订阅事件");
                    String content = "Mo宝支付（www.mobaopay.com）感谢您的关注！\n" +
                            "商务合作、疑问咨询请拨打客服热线：4006771357,或登录在线客服：（QQ）4006771357，我们将为您提供最为专业的第三方支付服务！";

                    return replyTextMessage(content);

                }
            // 模板消息发送任务完成事件
            } else if (event.equals(WXTemplateSendJobFinishEvent.TEMPLATE_SEND_JOB_FINISH)) {
                logger.trace("模板消息推送完成");
                WXTemplateSendJobFinishEvent sendJobFinishEvent = WXUtils.convertToBean(receiveXml, WXTemplateSendJobFinishEvent.class);
                logger.trace("消息{}, 推送状态: {}", sendJobFinishEvent.getMsgId(), sendJobFinishEvent.getStatus());
            }

        }
        return "success";
    }
}

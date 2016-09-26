package com.ruby.wechat.api.service.impl;

import com.ruby.wechat.api.dto.WXReceiveTextMessage;
import com.ruby.wechat.api.service.WXAutoReplyService;
import com.ruby.wechat.utils.WXReceiveMessageType;
import com.ruby.wechat.utils.WXUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by ruby on 2016/9/26.
 * Email:liyufeng_23@163.com
 */
@Service
public class WXAutoReplyServiceImpl implements WXAutoReplyService {

    private static final Logger logger = LoggerFactory.getLogger(WXAutoReplyServiceImpl.class);

    /**
     * 暂时只处理普通文本消息，其它类型消息将被忽略
     * @param receiveXml 请求报文
     * @return
     * @throws Exception
     */
    @Override
    public String getAutoReplyMessage(String receiveXml) throws Exception {

        // 生成时间戳
        String timestamp = String.valueOf(System.currentTimeMillis());

        //获取消息类型
        String msgType = WXUtil.getTagValue(receiveXml, "MsgType");

        if(msgType.equals(WXReceiveMessageType.TEXT_MESSAGE.toString())) {
            logger.trace("消息类型是: 普通文本消息");
            WXReceiveTextMessage message = WXUtil.convertToBean(receiveXml, WXReceiveTextMessage.class);

            String content = message.getContent();

            String reply = "";

            //Todo 此段逻辑应使用规则自动从配置或数据库中读取
            if (content.contains("你好")) {
                reply = "您好！欢迎关注Mo宝支付，祝您使用愉快！";
            }

            if (StringUtils.isNotBlank(reply)) {
                String format = "<xml><ToUserName><![CDATA[%1$s]]></ToUserName><FromUserName><![CDATA[%2$s]]></FromUserName><CreateTime>%3$s</CreateTime><MsgType><![CDATA[%4$s]]></MsgType><Content><![CDATA[%5$s]]></Content></xml>";
                String replyXml = String.format(format, message.getFromUserName(), message.getToUserName(), timestamp, "text", reply);

                return replyXml;
            }
        }

        return null;
    }
}

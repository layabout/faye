package com.ruby.wechat.api.service.impl;

import com.ruby.wechat.api.dto.WXReplyTextMessage;
import com.ruby.wechat.api.service.WXReplyMessageService;
import com.ruby.wechat.utils.WXBizMsgCrypt;
import com.ruby.wechat.utils.WXUtil;
import org.springframework.stereotype.Service;

/**
 * Created by ruby on 2016/9/26.
 * Email:liyufeng_23@163.com
 */
@Service
public class WXReplyMessageServiceImpl implements WXReplyMessageService{

    //加密模式
    public static String DECRYPT_TYPE = "aes";

    //文本消息
    private static final String TEXT = "text";

    //图片消息
    private static final String IMAGE = "image";

    //语音消息
    private static final String VOICE = "voice";

    //视频消息
    private static final String VIDEO = "video";

    //音乐消息
    private static final String MUSIC = "music";

    //图文消息
    private static final String NEWS = "news";

    @Override
    public String replyTextMessage(WXReplyTextMessage message) throws Exception {

        String format = "<xml><ToUserName><![CDATA[%1$s]]></ToUserName><FromUserName><![CDATA[%2$s]]></FromUserName><CreateTime>%3$s</CreateTime><MsgType><![CDATA[%4$s]]></MsgType><Content><![CDATA[%5$s]]></Content></xml>";
        String replyXml = String.format(format, message.getToUserName(), message.getFromUserName(), message.getCreateTime(), message.getMsgType(), message.getContent());

        if (DECRYPT_TYPE.equals("aes")) {
            //生成随机数
            String nonce = WXUtil.getRandomNum(9);

            WXBizMsgCrypt msgCrypt = WXUtil.getWxBizMsgCryptInstance();
            replyXml = msgCrypt.encryptMsg(replyXml, message.getCreateTime(), nonce);
        }

        return replyXml;
    }
}

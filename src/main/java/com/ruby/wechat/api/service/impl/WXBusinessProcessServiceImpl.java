package com.ruby.wechat.api.service.impl;

import com.ruby.wechat.api.service.WXAutoReplyService;
import com.ruby.wechat.api.service.WXBusinessProcessService;
import com.ruby.wechat.utils.WXBizMsgCrypt;
import com.ruby.wechat.utils.WXUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ruby on 2016/9/26.
 * Email:liyufeng_23@163.com
 */
@Service("wxBusinessProcessService")
public class WXBusinessProcessServiceImpl implements WXBusinessProcessService {

    private static final Logger logger = LoggerFactory.getLogger(WXBusinessProcessServiceImpl.class);

    @Autowired
    private WXAutoReplyService wxAutoReplyService;

    @Override
    public String replyMessage(String receiveXml) throws Exception{

        String replyXml = wxAutoReplyService.getAutoReplyMessage(receiveXml);
        logger.trace("回复明文消息: {}", replyXml);
        return replyXml;

    }

    @Override
    public String replyEncryptedMessage(String receiveXml) throws Exception{

        String replyXml = replyMessage(receiveXml);

        //获取时间戳
        String timestamp = WXUtil.getTagValue(replyXml, "CreateTime");
        //生成随机数
        String nonce = WXUtil.getRandomNum(9);

        WXBizMsgCrypt msgCrypt = WXUtil.getWxBizMsgCryptInstance();
        String decryptXml = msgCrypt.encryptMsg(replyXml, timestamp, nonce);

        logger.trace("回复加密消息: {}", decryptXml);

        return decryptXml;
    }

    @Override
    public String sendTemplateMessage(String templateId) {
        return null;
    }
}

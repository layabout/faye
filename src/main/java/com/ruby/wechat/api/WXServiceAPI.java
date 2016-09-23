package com.ruby.wechat.api;

import com.ruby.wechat.Constants;
import com.ruby.wechat.api.dto.WXTextMessage;
import com.ruby.wechat.api.manager.WXMessageManager;
import com.ruby.wechat.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ruby on 2016/8/5.
 * Email:liyufeng_23@163.com
 */
@RestController
public class WXServiceAPI {

    private static final Logger logger = LoggerFactory.getLogger(WXServiceAPI.class);

    /**
     * 服务器地址验证
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     */
    @RequestMapping("/api")
    public String index(@RequestParam(value="signature") String signature, @RequestParam(value = "timestamp") String timestamp,
                        @RequestParam(value = "nonce") String nonce, @RequestParam(value="echostr") String echostr) {

        logger.trace("接收参数[signature={}, timestamp={}, nonce={}, echostr={}]",signature, timestamp, nonce, echostr);

        String token = Constants.WX_TOKEN;

        try {
            String dev_signature = SHA1.getSHA1(token, timestamp, nonce);

            logger.trace("本地参数签名值:{}", dev_signature);
            logger.trace("远程参数签名值:{}", signature);

            if(dev_signature.equals(signature)) {

                logger.trace("signature verification");
                return echostr;

            } else {
                logger.trace("signature failure");
            }

        } catch (AesException e) {
            e.printStackTrace();
            logger.error("签名生成错误！");
        }

        return null;
    }

    /**
     * 微信公众号业务接口
     * @return
     */
    @RequestMapping(value = "/api", method = RequestMethod.POST)
    public String service(@RequestBody String requestBody, @RequestParam(value = "timestamp") String timestamp,
                          @RequestParam(value = "nonce") String nonce, HttpServletRequest request) {

        String transMode = request.getParameter("encrypt_type");
        if (StringUtils.isBlank(transMode) || transMode.equals("raw")) {
            logger.trace("明文模式");
            return null;
        } else {
            logger.trace("密文模式");
            try {
                logger.trace("原始密文: {}", requestBody);

                String msg_signature = request.getParameter("msg_signature");

                logger.trace("接收参数[msg_signature={}, timestamp={}, nonce={}]", msg_signature, timestamp, nonce);

                WXBizMsgCrypt msgCrypt = WXUtil.getWxBizMsgCryptInstance();
                String decryptXml = msgCrypt.decryptMsg(msg_signature, timestamp, nonce, requestBody);

                logger.trace("明文消息: {}", decryptXml);

                String msgType = WXUtil.getTagValue(decryptXml,"MsgType");

                if(msgType.equals(WXMessageType.TEXT_MESSAGE.toString()) ) {
                    logger.trace("消息类型是: 普通文本消息");
                    WXTextMessage textMessage = WXUtil.convertXmlToBean(decryptXml, WXTextMessage.class);

                    String replayMsg = "";
                    if(textMessage.getContent().contains("你好")) {
                        replayMsg = WXMessageManager.replayTextMessage(textMessage.getFromUserName(), textMessage.getToUserName(), "好锤子！");
                    } else {
                        replayMsg = WXMessageManager.replayTextMessage(textMessage.getFromUserName(), textMessage.getToUserName(), "您好！我暂时还不能回答您的问题哦，请拨打客服电话400-677-1357");
                    }

                    return replayMsg;
                } else {
                    //暂不处理除文本消息外的其它消息类型
                    return null;
                }

            } catch (Exception e) {
                logger.error("消息解密错误！");
                e.printStackTrace();
            }
        }
        return null;
    }

}

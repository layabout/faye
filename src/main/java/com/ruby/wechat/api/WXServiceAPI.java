package com.ruby.wechat.api;

import com.google.common.base.Throwables;
import com.ruby.wechat.Constants;
import com.ruby.wechat.api.dto.RestApiError;
import com.ruby.wechat.api.dto.WXReceiveTextMessage;
import com.ruby.wechat.api.dto.WXReplyTextMessage;
import com.ruby.wechat.api.service.WXReplyMessageService;
import com.ruby.wechat.utils.AesException;
import com.ruby.wechat.utils.SHA1;
import com.ruby.wechat.utils.WXBizMsgCrypt;
import com.ruby.wechat.utils.WXUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ruby on 2016/8/5.
 * Email:liyufeng_23@163.com
 */
@RestController
public class WXServiceAPI {

    private static final Logger logger = LoggerFactory.getLogger(WXServiceAPI.class);

    @Autowired
    private WXReplyMessageService wxReplyMessageService;


    //文本消息
    private static final String TEXT = "text";

    //图片消息
    private static final String IMAGE = "image";

    //语音消息
    private static final String VOICE = "voice";

    //视频消息
    private static final String VIDEO = "video";

    //小视频消息
    private static final String SHORT_VIDEO = "shortvideo";

    //地理位置消息
    private static final String LOCATION = "location";

    //链接消息
    private static final String LINK = "link";


    //事件推送
    private static final String EVENT = "event";

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

        String encryptType = request.getParameter("encrypt_type");
        if (StringUtils.isBlank(encryptType) || encryptType.equals("raw")) {

            //明文模式暂不处理
            logger.trace("明文模式");
            return "success";

        } else {

            logger.trace("密文模式");

            try {
                logger.trace("原始密文: {}", requestBody);

                String msg_signature = request.getParameter("msg_signature");

                logger.trace("接收参数[msg_signature={}, timestamp={}, nonce={}]", msg_signature, timestamp, nonce);

                WXBizMsgCrypt msgCrypt = WXUtil.getWxBizMsgCryptInstance();
                String decryptXml = msgCrypt.decryptMsg(msg_signature, timestamp, nonce, requestBody);

                logger.trace("明文消息: {}", decryptXml);

                //获取消息类型
                String msgType = WXUtil.getTagValue(decryptXml, "MsgType");

                if (msgType.equals(TEXT)) {

                    logger.trace("接收文本消息");

                    WXReceiveTextMessage message = WXUtil.convertToBean(decryptXml, WXReceiveTextMessage.class);

                    if (message.getContent().contains("你好")) {

                        WXReplyTextMessage reply = new WXReplyTextMessage();
                        reply.setToUserName(message.getFromUserName());
                        reply.setFromUserName(message.getToUserName());
                        reply.setContent("您好!欢迎关注Mo宝!");

                        return wxReplyMessageService.replyTextMessage(reply);
                    }

                } else if(msgType.equals(EVENT)) {

                    logger.trace("接收事件推送");

                }

            } catch (Exception e) {
                logger.error("消息解密错误！");
                return "failure";
            }
        }
        return "success";
    }

    /**
     * Restful API 统一异常处理
     * @param request
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    public RestApiError<String> ExceptionHandler(HttpServletRequest request, Exception exception) {

        RestApiError<String> err = new RestApiError<String>();
        err.setMessage(exception.getMessage());
        err.setCode(err.ERROR);
        err.setData("test data");
        err.setUrl(request.getRequestURL().toString());

        Throwable rootCause = Throwables.getRootCause(exception);

        logger.error(rootCause.toString(), exception);

        return err;
    }

}

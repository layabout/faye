package com.ruby.wechat.api;

import com.google.common.base.Throwables;
import com.ruby.wechat.Constants;
import com.ruby.wechat.api.dto.RestApiError;
import com.ruby.wechat.api.service.WXBusinessProcessService;
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
    private WXBusinessProcessService wxBusinessProcessService;

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

                return wxBusinessProcessService.replyEncryptedMessage(decryptXml);

            } catch (Exception e) {
                logger.error("消息解密错误！");
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Restful API 统一异常处理
     * @param request
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    public RestApiError<String> runtimeExceptionHandler(HttpServletRequest request, Exception exception) {

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

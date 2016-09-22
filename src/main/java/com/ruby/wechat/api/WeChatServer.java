package com.ruby.wechat.api;

import com.ruby.wechat.Constants;
import com.ruby.wechat.api.dto.WXEncryptMessage;
import com.ruby.wechat.utils.AesException;
import com.ruby.wechat.utils.SHA1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ruby on 2016/8/5.
 * Email:liyufeng_23@163.com
 */
@RestController
public class WeChatServer {

    private static final Logger logger = LoggerFactory.getLogger(WeChatServer.class);

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
     * @param wxEncryptMessage
     * @param timestamp
     * @param nonce
     * @param encrypt_type
     * @param msg_signature
     * @return
     */
    @RequestMapping(value = "/api", method = RequestMethod.POST)
    public String service(@RequestBody WXEncryptMessage wxEncryptMessage, @RequestParam(value = "timestamp") String timestamp,
                          @RequestParam(value = "nonce") String nonce, @RequestParam(value = "encrypt_type") String encrypt_type, @RequestParam(value = "msg_signature") String msg_signature) {

        //安全模式 aes | raw - 明文
        if (encrypt_type.equals("aes")) {
            try {
                String dev_msg_signature = SHA1.getSHA1(Constants.WX_TOKEN, timestamp, nonce, wxEncryptMessage.getEncrypt());
                logger.info(dev_msg_signature);
                if (msg_signature.equals(dev_msg_signature)) {
                    logger.info("签名验证通过");

                } else {

                    logger.error("签名验证失败");

                }
            } catch (AesException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}

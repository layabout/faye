package com.ruby.wechat.api;

import com.ruby.wechat.Constants;
import com.ruby.wechat.utils.AesException;
import com.ruby.wechat.utils.SHA1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public String index(@RequestParam(value="signature") String signature, @RequestParam(value = "timestamp") String timestamp, @RequestParam(value = "nonce") String nonce, @RequestParam(value="echostr") String echostr) {

        logger.trace("接收参数[signature={}, timestamp={}, nonce={}, echostr={}]",signature, timestamp, nonce, echostr);

        String token = Constants.WX_TOKEN;

        try {
            String sign = SHA1.getSHA1(token, timestamp, nonce);

            logger.trace("本地参数签名值:{}", sign);
            logger.trace("远程参数签名值:{}", signature);

            if(sign.equals(signature)) {
                logger.trace("signature verification");
                //start business process

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
}

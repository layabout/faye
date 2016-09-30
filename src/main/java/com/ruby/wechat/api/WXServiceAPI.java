package com.ruby.wechat.api;

import com.google.common.base.Throwables;
import com.ruby.common.exception.BusinessException;
import com.ruby.wechat.Constants;
import com.ruby.wechat.api.dto.ApiRespData;
import com.ruby.wechat.api.dto.TemplateData;
import com.ruby.wechat.api.dto.TemplateMessage;
import com.ruby.wechat.api.service.MerchantBindService;
import com.ruby.wechat.api.service.WXMessageService;
import com.ruby.wechat.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by ruby on 2016/8/5.
 * Email:liyufeng_23@163.com
 */
@RestController
public class WXServiceAPI {

    private static final Logger logger = LoggerFactory.getLogger(WXServiceAPI.class);

    @Autowired
    private WXMessageService wxMessageService;

    @Autowired
    private MerchantBindService merchantBindService;

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
     * 微信公众号消息接收接口
     * @return
     */
    @RequestMapping(value = "/api", method = RequestMethod.POST)
    public String receiver(@RequestBody String requestBody, @RequestParam(value = "timestamp") String timestamp,
                          @RequestParam(value = "nonce") String nonce, HttpServletRequest request) {

        String encryptType = request.getParameter("encrypt_type");
        if (StringUtils.isBlank(encryptType) || encryptType.equals("raw")) {

            // 明文模式暂不处理
            logger.trace("明文模式");
            return "success";

        } else {

            logger.trace("密文模式");

            try {

                logger.trace("原始密文: {}", requestBody);

                String msg_signature = request.getParameter("msg_signature");

                logger.trace("接收参数[msg_signature={}, timestamp={}, nonce={}]", msg_signature, timestamp, nonce);

                WXBizMsgCrypt msgCrypt = WXUtils.getWxBizMsgCryptInstance();
                String decryptXml = msgCrypt.decryptMsg(msg_signature, timestamp, nonce, requestBody);

                logger.trace("明文消息: {}", decryptXml);

                //处理消息
                return wxMessageService.processor(decryptXml);

            } catch (Exception e) {
                logger.error("消息解密错误！");
            }
        }
        return "success";
    }

    /**
     * 模板消息通知接口
     * @param message 消息体
     * @param request
     * 请求报文example
     * <xml>
     *      <touser>tracy</touser>
     *      <template>tmp001</template>
     *      <data>
     *          <item>
     *              <mark>first</mark>
     *              <value>支付成功</value>
     *          </item>
     *          <item>
     *              <mark>remark</mark>
     *              <value>欢迎再次购买!</value>
     *          </item>
     *      </data>
     * </xml>
     * @return
     */
    @RequestMapping(value = "/api/template/send", method = RequestMethod.POST)
    public String sendTemplateMsg(@RequestBody TemplateMessage message, HttpServletRequest request) throws Exception{

        //数据检验
        //龊逼方案,比较理想的是jaxb结合schema完成校验
        List<ErrorType> errors = new ArrayList<ErrorType>();

        if (StringUtils.isBlank(message.getTouser())) {
            errors.add(ErrorType.missing_param_touser);
        }

        if (StringUtils.isBlank(message.getTemplate())) {
            errors.add(ErrorType.missing_param_template);
        } else {
            if (NotificationType.getTemplate(message.getTemplate()) == null)
                errors.add(ErrorType.bad_param_template);
        }

        List<TemplateData> dataList = message.getItems();
        if (dataList == null || dataList.size() == 0) {
            errors.add(ErrorType.missing_param_data);
        } else {
            for(TemplateData item : dataList) {
                if (StringUtils.isBlank(item.getMark()))
                    errors.add(ErrorType.missing_param_mark);
                if (StringUtils.isBlank(item.getValue()))
                    errors.add(ErrorType.missing_param_value);
            }
        }

        if (errors.size() != 0) {
            ErrorType errorType = errors.get(0);
            throw new BusinessException(errorType.getMessage(), errorType.getCode());
        }

        //调用模板通知发送服务
        return wxMessageService.sendTemplateMessage(message);

    }

    /**
     * 商户绑定接口
     * @return
     */
    @RequestMapping(value = "/api/wx/user/bind", method = RequestMethod.POST)
    public ApiRespData<String> userBind(HttpServletRequest request) throws Exception{

        //数据校验
        List<ErrorType> errors = new ArrayList<ErrorType>();

        String loginId = request.getParameter("loginId");
        if (StringUtils.isBlank(loginId)) {
            errors.add(ErrorType.missing_param_loginId);
        } else {
            if (!Pattern.matches("^[a-zA-Z0-9_\\-@\\.]{6,30}$", loginId)) {
                errors.add(ErrorType.bad_param_loginId);
            }
        }

        String openId = request.getParameter("openId");
        if (StringUtils.isBlank(openId)) {
            errors.add(ErrorType.missing_param_openId);
        }

        String activeCode = request.getParameter("activeCode");
        if (StringUtils.isBlank(activeCode)) {
            errors.add(ErrorType.missing_param_activeCode);
        }

        String sendId = request.getParameter("sendId");
        if (StringUtils.isBlank(sendId)) {
            errors.add(ErrorType.missing_param_sendId);
        }

        if (errors.size() != 0) {
            ErrorType errorType = errors.get(0);
            throw new BusinessException(errorType.getMessage(), errorType.getCode());
        }

        //调用用户绑定服务
        return merchantBindService.userBind(loginId, openId, sendId, activeCode);
    }

    /**
     * 商户解绑接口
     * @return
     */
    @RequestMapping(value = "/api/wx/user/unbind/{openId}", method = RequestMethod.GET)
    public ApiRespData<String> userUnbind(@PathVariable String openId) {
        System.out.println(openId);
        return null;
    }

    /**
     * Restful API 统一异常处理
     * @param request
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ApiRespData<String> ExceptionHandler(HttpServletRequest request, Exception exception) {

        ApiRespData<String> error = new ApiRespData<String>();
        error.setMessage("请求发生异常！");
        error.setCode(error.ERROR);
        error.setData("error");
        error.setUrl(request.getRequestURL().toString());

        Throwable rootCause = Throwables.getRootCause(exception);

        logger.error(rootCause.toString(), exception);

        return error;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiRespData<String> httpMessageNotReadableExceptionHandler(Exception exception) {
        ApiRespData<String> error = new ApiRespData<String>();
        error.setMessage("请求报文缺失！");
        error.setCode(error.MESSAGE_MISSING);

        Throwable rootCause = Throwables.getRootCause(exception);

        logger.error(rootCause.toString(), exception);

        return error;
    }

    @ExceptionHandler(BusinessException.class)
    public ApiRespData<String> businessExceptionHandler(BusinessException exception) {
        ApiRespData<String> error = new ApiRespData<String>();
        error.setMessage(exception.getMessage());
        error.setCode(exception.getErrorCode());

        Throwable rootCause = Throwables.getRootCause(exception);

        logger.error(rootCause.toString(), exception);

        return error;
    }

}

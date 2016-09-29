package com.ruby.wechat.api;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Throwables;
import com.ruby.common.exception.BusinessException;
import com.ruby.wechat.Constants;
import com.ruby.wechat.api.dto.*;
import com.ruby.wechat.api.manager.WXAccessTokenManager;
import com.ruby.wechat.api.service.WXMessageService;
import com.ruby.wechat.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ruby on 2016/8/5.
 * Email:liyufeng_23@163.com
 */
@RestController
public class WXServiceAPI {

    private static final Logger logger = LoggerFactory.getLogger(WXServiceAPI.class);

    @Autowired
    private WXMessageService wxMessageService;

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
    public String sendTemplateMessage(@RequestBody TemplateMessage message, HttpServletRequest request) throws Exception{

        //数据检验
        //龊逼方案,比较理想的是jaxb结合schema完成校验
        ErrorType errorType = null;

        if (StringUtils.isBlank(message.getTouser())) {
            errorType = ErrorType.missing_param_touser;
        }

        if (StringUtils.isBlank(message.getTemplate())) {
            errorType = ErrorType.missing_param_template;
        } else {
            if (NotificationType.getTemplate(message.getTemplate()) == null)
                errorType = ErrorType.bad_param_template;
        }

        List<TemplateData> dataList = message.getItems();
        if (dataList == null || dataList.size() == 0) {
            errorType = ErrorType.missing_param_data;
        } else {
            for(TemplateData item : dataList) {
                if (StringUtils.isBlank(item.getMark()))
                    errorType = ErrorType.missing_param_mark;
                if (StringUtils.isBlank(item.getValue()))
                    errorType = ErrorType.missing_param_value;
            }
        }

        if (errorType != null)
            throw new BusinessException(errorType.getMessage(), errorType.getCode());

        //判断通知类型
        //TM001 - 交易通知
        //TM002 - 提现通知
        //TM003 - 转账通知
        String template = message.getTemplate();
        NotificationType notificationType = NotificationType.getNotificationType(template);
        logger.trace(notificationType.getDesc());

        //组装json报文
        WXTemplateMessage wxTemplateMessage = new WXTemplateMessage();
        wxTemplateMessage.setTouser(message.getTouser());
        wxTemplateMessage.setTemplate_id(notificationType.getTemplate_id());
        wxTemplateMessage.setUrl(notificationType.getUrl());

        List<TemplateData> list = message.getItems();

        Map<String, WXTemplateData> map = new HashMap<String, WXTemplateData>();

        for(TemplateData item : list) {
            WXTemplateData data = new WXTemplateData();
            data.setValue(item.getValue());
            //没有指定的字体颜色，则使用默认值
            if (StringUtils.isNotBlank(item.getColor()))
                data.setColor(item.getColor());
            else
                data.setColor("#173177");

            map.put(item.getMark(), data);
        }

        wxTemplateMessage.setData(map);
        //消息组装完毕

        //调用微信服务器接口，发送模板通知消息
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + WXAccessTokenManager.getAccessToken();

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(requestUrl);
        CloseableHttpResponse response = null;

        String json = JSON.toJSONString(wxTemplateMessage);
        logger.trace("请求json数据: {}", json);
        StringEntity entity = new StringEntity(json, "UTF-8");
        post.setEntity(entity);

        try {
            response = client.execute(post);
            String bodyAsString = EntityUtils.toString(response.getEntity());
            logger.trace("微信服务器返回结果: {}", bodyAsString);

            return bodyAsString;

        } catch (IOException e) {
            throw new Exception("微信接口调用失败");
        }

    }

    /**
     * 商户绑定接口
     * @return
     */
    @RequestMapping(value = "/api/wx/user/bind", method = RequestMethod.POST)
    public String userBind() {
        return null;
    }

    /**
     * 商户解绑接口
     * @return
     */
    @RequestMapping(value = "/api/wx/user/unbind/{openId}", method = RequestMethod.GET)
    public String userUnbind(@PathVariable String openId) {
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
        error.setMessage("请求异常！");
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

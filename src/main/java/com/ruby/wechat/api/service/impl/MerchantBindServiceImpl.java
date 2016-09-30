package com.ruby.wechat.api.service.impl;

import com.ruby.wechat.api.dto.ApiRespData;
import com.ruby.wechat.api.service.MerchantBindService;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

/**
 * Created by ruby on 2016/9/30.
 * Email:liyufeng_23@163.com
 */
@Service
public class MerchantBindServiceImpl extends BaseServceImpl implements MerchantBindService {

    @Override
    public ApiRespData<String> userBind(String loginId, String openId, String sendId, String activeCode) throws Exception {

        Context context = new Context();
        context.setVariable("loginId", loginId);
        context.setVariable("openID", openId);
        context.setVariable("sendID", sendId);
        context.setVariable("activeCode", activeCode);

        String reqXml = xmlTemplateEngine.process("32000044", context);

        String result = sendPostToMgrService(reqXml);

        System.out.println(reqXml);

        return null;
    }

    @Override
    public ApiRespData<String> userUnbind(String openId) {
        return null;
    }
}

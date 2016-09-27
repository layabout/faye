package com.ruby.wechat.api;

import com.ruby.wechat.api.dto.TemplateData;
import com.ruby.wechat.api.dto.TemplateMessage;
import com.ruby.wechat.api.dto.WXTemplateData;
import com.ruby.wechat.api.dto.WXTemplateMessage;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by ruby on 2016/9/27.
 * Email:liyufeng_23@163.com
 */
@RestController
public class TestAPI {

    @RequestMapping(value = "/api/template/send", method = RequestMethod.POST)
    public WXTemplateMessage test(@RequestBody TemplateMessage templateMessage) {


        WXTemplateMessage message = new WXTemplateMessage();
        message.setTouser(templateMessage.getTouser());
        message.setTemplate_id(templateMessage.getMsgtype()+"detail");
        message.setUrl("www.google.com");

        List<TemplateData> list = templateMessage.getItems();


        Map<String,WXTemplateData> map = new HashMap<String, WXTemplateData>();

        for(TemplateData item : list) {

            WXTemplateData data = new WXTemplateData();
            data.setValue(item.getValue());
            data.setColor("#fff");

            map.put(item.getName(),data);

        }

        message.setData(map);

        return message;
    }

    @RequestMapping(value = "/api/template", method = RequestMethod.GET)
    public TemplateMessage test2() {

        TemplateMessage message = new TemplateMessage();
        message.setTouser("tracy");
        message.setMsgtype("tmp001");

        TemplateData data1 = new TemplateData();
        data1.setName("first");
        data1.setValue("支付成功");

        TemplateData data2 = new TemplateData();
        data2.setName("remark");
        data2.setValue("欢迎再次购买!");

        List<TemplateData> list = new ArrayList<TemplateData>();
        list.add(data1);
        list.add(data2);

        message.setItems(list);

        return message;
    }
}

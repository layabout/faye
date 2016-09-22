package com.ruby.wechat.api.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by ruby on 2016/9/22.
 * Email:liyufeng_23@163.com
 */
@XmlRootElement
public class WXTextMessage extends WXBaseMessage {
    //文本消息内容
    private String content;

    public WXTextMessage() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

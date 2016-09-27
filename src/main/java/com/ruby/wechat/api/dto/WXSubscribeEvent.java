package com.ruby.wechat.api.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by ruby on 2016/9/27.
 * Email:liyufeng_23@163.com
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class WXSubscribeEvent extends WXMessageBase {

    //订阅
    public static final String SUBSCRIBE = "subscribe";
    //取消订阅
    public static final String UNSUBSCRIBE = "unsubscribe";

    @XmlElement(name = "Event")
    private String event;

    public WXSubscribeEvent() {
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}

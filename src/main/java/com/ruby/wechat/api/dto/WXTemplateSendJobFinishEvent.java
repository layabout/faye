package com.ruby.wechat.api.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by ruby on 2016/9/29.
 * Email:liyufeng_23@163.com
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class WXTemplateSendJobFinishEvent extends WXMessageBase {

    //消息发送完成推送消息
    public static final String TEMPLATE_SEND_JOB_FINISH = "TEMPLATESENDJOBFINISH";

    @XmlElement(name = "Event")
    private String event;

    @XmlElement(name = "MsgID")
    private String msgId;

    @XmlElement(name = "Status")
    private String status;

    public WXTemplateSendJobFinishEvent() {
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

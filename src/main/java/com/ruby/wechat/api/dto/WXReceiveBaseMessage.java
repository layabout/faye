package com.ruby.wechat.api.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by ruby on 2016/9/22.
 * Email:liyufeng_23@163.com
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class WXReceiveBaseMessage {

    @XmlElement(name = "ToUserName")
    private String toUserName;

    @XmlElement(name = "FromUserName")
    private String fromUserName;
    //消息创建时间
    @XmlElement(name = "CreateTime")
    private String createTime;
    //消息类型
    @XmlElement(name = "MsgType")
    private String msgType;
    //消息id
    @XmlElement(name = "MsgId")
    private String msgId;

    public WXReceiveBaseMessage() {
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}

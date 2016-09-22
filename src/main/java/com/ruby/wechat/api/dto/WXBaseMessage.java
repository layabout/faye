package com.ruby.wechat.api.dto;

/**
 * Created by ruby on 2016/9/22.
 * Email:liyufeng_23@163.com
 */
public class WXBaseMessage {

    //开发者微信号
    private String toUserName;
    //发送方账号(一个OpenID)
    private String fromUserName;
    //消息创建时间
    private String createTime;
    //消息类型
    private String msgType;
    //消息id
    private String msgId;

    public WXBaseMessage() {
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

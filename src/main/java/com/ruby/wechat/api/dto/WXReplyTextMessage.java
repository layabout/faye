package com.ruby.wechat.api.dto;

/**
 * Created by ruby on 2016/9/26.
 * Email:liyufeng_23@163.com
 */
public class WXReplyTextMessage extends WXReplyBaseMessage {

    private String content;

    public WXReplyTextMessage() {

        // 生成时间戳
        String timestamp = String.valueOf(System.currentTimeMillis());
        super.setCreateTime(timestamp);
        super.setMsgType("text");
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

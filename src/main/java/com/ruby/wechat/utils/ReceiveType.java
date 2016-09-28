package com.ruby.wechat.utils;

/**
 * 接收消息枚举类型
 * Created by ruby on 2016/9/27.
 * Email:liyufeng_23@163.com
 */
public enum ReceiveType {

    TEXT("文本消息", "text"), IMAGE("图片消息", "image"), VOICE("语音消息", "voice"), VIDEO("视频消息", "video"),
    SHORT_VIDEO("短视频消息", "shortvideo"), LOCATION("地理位置消息", "location"), LINK("链接消息", "link"),
    EVENT("事件推送", "event");

    private String desc;

    private String type;

    ReceiveType(String desc, String type) {
        this.desc = desc;
        this.type = type;
    }

    public static String getDesc(String type) {
        for (ReceiveType c : ReceiveType .values()) {
            if (c.getType().equals(type)) {
                return c.desc;
            }
        }
        return null;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString(){
        return this.type;
    }

}

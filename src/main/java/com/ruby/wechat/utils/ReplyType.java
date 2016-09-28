package com.ruby.wechat.utils;

/**
 * Created by ruby on 2016/9/27.
 * Email:liyufeng_23@163.com
 */
public enum ReplyType {

    TEXT("文本消息", "text"), IMAGE("图片消息", "image"), VOICE("语音消息", "voice"), VIDEO("视频消息", "video"),
    MUSIC("音乐消息", "music"), NEWS("图文消息", "news");

    private String desc;

    private String type;

    ReplyType(String desc, String type) {
        this.desc = desc;
        this.type = type;
    }

    public static String getDesc(String type) {
        for (ReplyType c : ReplyType .values()) {
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

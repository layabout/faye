package com.ruby.wechat.utils;

/**
 * Created by ruby on 2016/9/23.
 * Email:liyufeng_23@163.com
 * 1 文本消息
 * 2 图片消息
 * 3 语音消息
 * 4 视频消息
 * 5 小视频消息
 * 6 地理位置消息
 * 7 链接消息
 */
public enum WXMessageType {

    TEXT_MESSAGE("text"), IMAGE_MESSAGE("image"), VOICE_MESSAGE("voice"), VIDEO_MESSAGE("video"), SHORTVIDEO_MESSAGE("shortvideo"), LOCATION_MESSAGE("location"), LINK_MESSAGE("link");

    private String value;

    WXMessageType(String _val) {
        this.value = _val;
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }
}

package com.ruby.wechat.utils;

/**
 * Created by ruby on 2016/9/23.
 * Email:liyufeng_23@163.com
 * 1 文本消息
 * 2 图片消息
 * 3 语音消息
 * 4 视频消息
 * 5 音乐消息
 * 6 图文消息
 */
public enum WXReplyMessageType {

    TEXT_MESSAGE("text"), IMAGE_MESSAGE("image"), VOICE_MESSAGE("voice"), VIDEO_MESSAGE("video"),  MUSIC_MESSAGE("music"), NEWS_MESSAGE("news");

    private String value;

    WXReplyMessageType(String _val) {
        this.value = _val;
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }
}

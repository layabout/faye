package com.ruby.wechat.api.service;

import com.ruby.wechat.api.dto.WXReplyTextMessage;

/**
 * 被动消息回复接口
 * Created by ruby on 2016/9/26.
 * Email:liyufeng_23@163.com
 */
public interface WXReplyMessageService {

    /**
     * 回复文本消息
     * @param message
     * @return
     */
    String replyTextMessage(WXReplyTextMessage message) throws Exception;

}

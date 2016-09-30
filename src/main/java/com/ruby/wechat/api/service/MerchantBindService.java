package com.ruby.wechat.api.service;

import com.ruby.wechat.api.dto.ApiRespData;

/**
 * 商户绑定服务
 * Created by ruby on 2016/9/30.
 * Email:liyufeng_23@163.com
 */
public interface MerchantBindService extends BaseService{

    /**
     * 绑定接口
     * @param loginId 商户登录id
     * @param openId  微信用户标识
     * @param sendId
     * @param activeCode 短信验证码
     * @return
     */
    ApiRespData<String> userBind(String loginId, String openId, String sendId, String activeCode) throws Exception;

    /**
     * 解绑接口
     * @param openId
     * @return
     */
    ApiRespData<String> userUnbind(String openId) throws Exception;
}

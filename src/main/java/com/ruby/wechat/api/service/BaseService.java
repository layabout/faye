package com.ruby.wechat.api.service;

/**
 * Created by ruby on 2016/9/30.
 * Email:liyufeng_23@163.com
 */
public interface BaseService {

    /**
     * 向管理服务发送post请求
     * @param data
     * @return
     */
    String sendPostToMgrService(String data) throws Exception;

    /**
     *  发送post请求
     * @param data
     * @param url
     * @return
     * @throws Exception
     */
    String sendPost(String data, String url) throws Exception;

}

package com.ruby.wechat.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by ruby on 2016/9/28.
 * Email:liyufeng_23@163.com
 */
public class ApiRespData<T> {

    public static final String BUSY = "-1";
    public static final String OK = "0";
    public static final String ERROR = "100";

    public static final String MESSAGE_MISSING = "101";

    private String message;

    private String code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String url;

    public ApiRespData() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

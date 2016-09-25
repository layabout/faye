package com.ruby.wechat.api.dto;

/**
 * Created by ruby on 2016/9/25.
 * Email:liyufeng_23@163.com
 */
public class RestApiError<T> {

    public static final Integer OK = 0;
    public static final Integer ERROR = 100;

    private String message;
    private Integer code;
    private T data;
    private String url;

    public RestApiError() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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

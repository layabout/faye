package com.ruby.wechat.utils;

/**
 * 错误消息枚举
 * Created by ruby on 2016/9/28.
 * Email:liyufeng_23@163.com
 */
public enum  ErrorType {

    //规则：倒数第三位 1 - 参数缺失； 2 - 值域错误
    missing_param_touser("缺少touser参数", "40100"),
    missing_param_template("缺少template参数", "40101"),
    missing_param_data("缺少data参数", "40102"),
    missing_param_mark("缺少mark参数", "40103"),
    missing_param_value("缺少value参数", "40104"),
    missing_param_loginId("登录账号不能为空", "40105"),
    missing_param_openId("缺少openId参数", "40106"),
    missing_param_sendId("缺少sendId参数", "40107"),
    missing_param_activeCode("短信验证码不能为空", "40108"),

    bad_param_template("template参数值错误", "40200"),
    bad_param_loginId("登录账号格式错误", "40201");

    private String message;
    private String code;

    ErrorType(String message, String code) {
        this.message = message;
        this.code = code;
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

    public static String getMessage(String code) {
        for (ErrorType c : ErrorType .values()) {
            if (c.getCode().equals(code)) {
                return c.message;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.code;
    }
}

package com.ruby.common.exception;

/**
 * Created by ruby on 2016/5/25.
 * Email:liyufeng_23@163.com
 */
public class ParamsException extends RuntimeException {
    protected String errorCode = "UNKNOWN_ERROR";

    protected String[] errorArgs = null;

    protected String errorMessage = null;

    public ParamsException(String message) {
        this.errorMessage = message;
    }

    public ParamsException(String message, String code) {
        this.errorMessage = message;
        this.errorCode = code;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }

    public String getErrorCode() { return this.errorCode; }
}

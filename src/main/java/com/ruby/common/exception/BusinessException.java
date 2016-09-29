package com.ruby.common.exception;

/**
 * Created by ruby on 2016/5/25.
 * Email:liyufeng_23@163.com
 */
public class BusinessException extends RuntimeException {

    protected String errorCode = "UNKNOWN_ERROR";

    protected String[] errorArgs = null;

    protected String errorMessage = null;

    public BusinessException() {
        super();
    }

    public BusinessException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public BusinessException(String message, String code) {
        this.errorMessage = message;
        this.errorCode = code;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }

    public String[] getErrorArgs() {
        return errorArgs;
    }

    public void setErrorArgs(String[] errorArgs) {
        this.errorArgs = errorArgs;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;

    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}

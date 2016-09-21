package com.ruby.common.exception;

/**
 * Created by ruby on 2016/5/25.
 * Email:liyufeng_23@163.com
 */
public class BusinessException extends RuntimeException{

    protected String errorCode = "UNKNOWN_ERROR";

    protected String[] errorArgs = null;

    protected String errorMessage = null;

    public BusinessException() {
        super();
    }

    public BusinessException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }

}

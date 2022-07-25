package com.qn.computersell.service.ex;

public class ServiceException extends RuntimeException{
    //无任何提示
    public ServiceException() {
        super();
    }
    //提示异常信息
    public ServiceException(String message) {
        super(message);
    }
   //
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

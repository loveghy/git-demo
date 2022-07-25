package com.qn.computersell.service.ex;

public class CartNotfoundException extends ServiceException{
    public CartNotfoundException() {
    }

    public CartNotfoundException(String message) {
        super(message);
    }

    public CartNotfoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CartNotfoundException(Throwable cause) {
        super(cause);
    }

    public CartNotfoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

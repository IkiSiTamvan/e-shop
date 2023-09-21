package com.sitamvan.eshop.util;

public class HandledException extends Exception{
    private int code;

    public HandledException(ErrorType errorType){
        super(errorType.getErrorMessage());
        this.code = errorType.getValue();
    }

    public int getCode() {
        return code;
    }
}

package com.sitamvan.eshop.util;

import org.springframework.http.HttpStatus;

public enum ErrorType {
    
    ITEM_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Item not found"),
    CUSTOMER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Customer not found"),
    ITEM_INSUFFICIENT(HttpStatus.BAD_REQUEST.value(), "Insufficient item stock"),
    ITEM_NOT_IN_CHART(HttpStatus.BAD_REQUEST.value(), "Item not in the cart"),
    CART_IS_EMPTY(HttpStatus.BAD_REQUEST.value(), "Cart is empty");

    private final int value;
    private final String errorMessage;

    private ErrorType(int value, String message){
        this.value = value;
        this.errorMessage = message;
    }

    public int getValue() {
        return value;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    
}

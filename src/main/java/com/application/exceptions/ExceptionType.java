package com.application.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
//Creating our personal Exception
@Getter
@RequiredArgsConstructor
public enum ExceptionType {

    ADMINISTRATOR_NOT_FOUND(HttpStatus.NOT_FOUND, "Administrator not found"),
    WAITER_NOT_FOUND(HttpStatus.NOT_FOUND, "Waiter not found"),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "Product not found"),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "Order not found"),
    TABLE_NOT_FOUND(HttpStatus.NOT_FOUND, "Table not found"),
    CHECKOUT_NOT_FOUND(HttpStatus.NOT_FOUND, "Checkout not found");

    private final HttpStatus httpStatus;
    private final String message;
}

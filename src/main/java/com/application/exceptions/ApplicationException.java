package com.application.exceptions;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException{
    private final ExceptionType exceptionType;

    public ApplicationException(ExceptionType exceptionType){
        this.exceptionType = exceptionType;
    }

    public ApplicationException(String message, ExceptionType exceptionType) {
        super(message);
        this.exceptionType = exceptionType;
    }

    public ApplicationException(String message, Throwable cause, ExceptionType exceptionType) {
        super(message, cause);
        this.exceptionType = exceptionType;
    }

    public ApplicationException(Throwable cause, ExceptionType exceptionType) {
        super(cause);
        this.exceptionType = exceptionType;
    }

    public ApplicationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ExceptionType exceptionType) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.exceptionType = exceptionType;
    }
}

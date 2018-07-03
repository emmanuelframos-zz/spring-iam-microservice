package com.exception;

public class BusinessException extends Exception {

    private ExceptionMessages exceptionMessages;

    private BusinessException(String message){
        super(message);
    }

    public BusinessException(ExceptionMessages exceptionMessages){
        this(exceptionMessages.getMessage());
        this.exceptionMessages = exceptionMessages;
    }

    public ExceptionMessages getExceptionMessages() {
        return this.exceptionMessages;
    }
}
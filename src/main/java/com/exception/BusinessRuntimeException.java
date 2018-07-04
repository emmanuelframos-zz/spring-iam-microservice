package com.exception;

public class BusinessRuntimeException extends RuntimeException {

    private ExceptionMessages exceptionMessages;

    private BusinessRuntimeException(String message){
        super(message);
    }

    public BusinessRuntimeException(ExceptionMessages exceptionMessages){
        this(exceptionMessages.getMessage());
        this.exceptionMessages = exceptionMessages;
    }

    public ExceptionMessages getExceptionMessages() {
        return this.exceptionMessages;
    }
}
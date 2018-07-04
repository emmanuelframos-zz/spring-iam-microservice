package com.exception;

public class UnauthorizedException extends Exception {

    private ExceptionMessages exceptionMessages;

    private UnauthorizedException(String message){
        super(message);
    }

    public UnauthorizedException(ExceptionMessages exceptionMessages){
        this(exceptionMessages.getMessage());
        this.exceptionMessages = exceptionMessages;
    }

    public ExceptionMessages getExceptionMessages() {
        return this.exceptionMessages;
    }
}

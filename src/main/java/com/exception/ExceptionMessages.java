package com.exception;

public enum ExceptionMessages {

    GENERIC("Um erro ocorreu"),
    INVALID_USER_OR_PASSWORD("Usuário e/ou senha inválidos"),
    EMAIL_ALREADY_EXISTS("E-mail já existente");

    private String message;

    ExceptionMessages(String message){
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }
}
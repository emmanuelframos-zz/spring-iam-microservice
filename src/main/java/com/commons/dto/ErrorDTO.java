package com.commons.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class ErrorDTO {

    private String message;

    private ErrorDTO(){}

    public static ErrorDTO build(){
        return new ErrorDTO();
    }

    public ErrorDTO message(String message){
        this.message = message;
        return this;
    }

    public String getMessage(){
        return this.message;
    }
}
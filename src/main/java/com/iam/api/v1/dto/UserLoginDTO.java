package com.iam.api.v1.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserLoginDTO {

    public String email;
    public String password;

    public UserLoginDTO(){
        //Avoid errors on parsing
    }

}

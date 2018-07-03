package com.iam.api.v1.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.utils.JsonDateDeserializer;
import com.utils.JsonDateSerializer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    public UUID id;

    public String name;

    public String email;

    public String password;

    public List<PhoneDTO> phones;

    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonDeserialize(using = JsonDateDeserializer.class)
    public Date created;

    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonDeserialize(using = JsonDateDeserializer.class)
    public Date lastModified;

    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonDeserialize(using = JsonDateDeserializer.class)
    public Date lastLogin;

    public UUID token;

    public UserDTO(){
        this.phones = new ArrayList<>();
    }
}
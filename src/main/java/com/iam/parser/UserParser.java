package com.iam.parser;

import com.iam.api.v1.dto.UserDTO;
import com.iam.domain.Phone;
import com.iam.domain.User;
import com.utils.BCryptUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class UserParser {

    public UserDTO toDto(User user){

        UserDTO userDTO = new UserDTO();
        userDTO.id = user.getUuid().toString();
        userDTO.created = user.getCreated();
        userDTO.lastLogin = user.getLastLogin();
        userDTO.lastModified = user.getLastModified();
        userDTO.token = user.getToken();

        return userDTO;
    }

    public User toDomain(UserDTO userDTO){

        User user = new User();
        user.setName(userDTO.name);
        user.setEmail(userDTO.email);
        user.setPassword(BCryptUtils.hash(userDTO.password, 12));
        user.setLastLogin(new Date());
        user.setToken(BCryptUtils.hash(UUID.randomUUID().toString(), 12));
        user.setUuid(UUID.randomUUID());

        userDTO.phones.forEach(p -> {
            Phone phone = new Phone();
            phone.setDdd(p.ddd);
            phone.setNumber(p.number);
            phone.setUser(user);
            user.addPhone(phone);
        });

        return user;
    }
}
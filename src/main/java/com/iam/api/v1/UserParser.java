package com.iam.api.v1;

import com.iam.api.v1.dto.PhoneDTO;
import com.iam.api.v1.dto.UserDTO;
import com.iam.domain.Phone;
import com.iam.domain.User;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component
public class UserParser {

    public UserDTO toDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.email = user.getEmail();
        userDTO.name = user.getEmail();
        userDTO.password = user.getPassword();
        userDTO.created = user.getCreated();
        userDTO.lastLogin = user.getLastLogin();
        userDTO.lastModified = user.getLastModified();
        userDTO.token = user.getToken();
        userDTO.phones = user.getPhones()
            .stream()
            .map(p -> {
                PhoneDTO phoneDTO = new PhoneDTO();
                phoneDTO.ddd = p.getDdd();
                phoneDTO.number = p.getNumber();
                return phoneDTO;
            }).collect(Collectors.toList());
        return userDTO;
    }

    public User toDomain(UserDTO userDTO){
        User user = new User();
        user.setName(userDTO.name);
        user.setEmail(userDTO.email);
        user.setPassword(userDTO.password);
        //TODO Check
        user.setLastLogin(new Date());
        user.setToken("TOKEN");

        userDTO.phones.forEach(p -> {
            Phone phone = new Phone();
            phone.setDdd(p.ddd);
            phone.setNumber(p.number);
            user.addPhone(phone);
        });
        return user;
    }
}
package com.iam.api.v1;

import com.exception.BusinessException;
import com.exception.EntityNotFoundException;
import com.exception.UnauthorizedException;
import com.iam.api.v1.dto.UserDTO;
import com.iam.api.v1.dto.UserLoginDTO;
import com.iam.domain.User;
import com.iam.parser.UserParser;
import com.iam.service.UserService;
import com.iam.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api/v1/iam", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
public class IAMRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserParser userParser;

    @Autowired
    private UserValidator userValidator;

    @PostMapping(value="/signup", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO signUp(@RequestBody UserDTO userDTO) throws BusinessException {

        userValidator.validateOnSignup(userDTO);

        User user = userParser.toDomain(userDTO);

        user = userService.signUp(user);

        return userParser.toDto(user);
    }

    @PostMapping(value="/login", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public UserDTO login(@RequestBody UserLoginDTO userLoginDTO)
            throws UnauthorizedException, EntityNotFoundException, BusinessException {

        userValidator.validateOnLogin(userLoginDTO);

        User user = userService.login(userLoginDTO);

        return userParser.toDto(user);
    }

    @GetMapping("/profile/{id}")
    public UserDTO profile(@RequestHeader(name=HttpHeaders.AUTHORIZATION, required=false) String token,
                           @PathVariable("id") String userUUID)
            throws BusinessException, UnauthorizedException {

        userValidator.validateProfile(token);

        User user = userService.profile(token, userUUID);

        return userParser.toDto(user);
    }
}
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/iam",
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class IAMRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserParser userParser;

    @Autowired
    private UserValidator userValidator;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody UserDTO userDTO) throws BusinessException {

        userValidator.validateOnSignup(userDTO);

        User user = userParser.toSave(userDTO);

        user = userService.signUp(user);

        return new ResponseEntity<>(userParser.toDto(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody UserLoginDTO userLoginDTO)
            throws UnauthorizedException, EntityNotFoundException, BusinessException {

        userValidator.validateOnLogin(userLoginDTO);

        User user = userService.login(userLoginDTO);

        return new ResponseEntity<>(userParser.toDto(user), HttpStatus.OK);
    }
}
package com.iam.api.v1;

import com.iam.api.v1.dto.UserDTO;
import com.iam.domain.User;
import com.iam.service.IAMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(value = "/api/v1/iam", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class IAMRestController {

    @Autowired
    private IAMService iamService;

    @Autowired
    private UserParser userParser;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody UserDTO userDTO){
        User user = userParser.toDomain(userDTO);
        user = iamService.signUp(user);
        return ok(userParser.toDTO(user));
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(){
        return ok("Login");
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> findAllUsers() {
        return ok(iamService.findAllUsers().stream().map(u -> userParser.toDTO(u)).collect(Collectors.toList()));
    }
}
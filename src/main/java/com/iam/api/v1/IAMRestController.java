package com.iam.api.v1;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class IAMRestController {

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(){
        return ok("Sign Up");
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(){
        return ok("Login");
    }
}
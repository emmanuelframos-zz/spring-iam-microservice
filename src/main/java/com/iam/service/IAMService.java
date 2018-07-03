package com.iam.service;

import com.iam.domain.User;
import com.iam.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IAMService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    @Transactional
    public User signUp(User user){
        return userRepository.save(user);
    }
}
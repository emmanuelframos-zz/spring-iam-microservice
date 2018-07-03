package com.iam.service;

import com.exception.BusinessException;
import com.exception.ExceptionMessages;
import com.iam.domain.User;
import com.iam.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> findAll(){
        return userRepository.findAll();
    }

    @Transactional
    public User signUp(User user) throws BusinessException {

        boolean userExists = findByEmailIgnoringCase(user) != null;

        if (userExists)
            throw new BusinessException(ExceptionMessages.EMAIL_ALREADY_EXISTS);

        return userRepository.save(user);
    }

    private User findByEmailIgnoringCase(User user){
        return userRepository.findByEmailIgnoreCase(user.getEmail());
    }
}
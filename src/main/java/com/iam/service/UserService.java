package com.iam.service;

import com.exception.BusinessException;
import com.exception.EntityNotFoundException;
import com.exception.ExceptionMessages;
import com.exception.UnauthorizedException;
import com.iam.api.v1.dto.UserLoginDTO;
import com.iam.domain.User;
import com.iam.repository.UserRepository;
import com.utils.BCryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User signUp(User user) throws BusinessException {

        boolean userExists = this.userRepository.findByEmailIgnoreCase(user.getEmail()) != null;

        if (userExists)
            throw new BusinessException(ExceptionMessages.EMAIL_ALREADY_EXISTS);

        return userRepository.save(user);
    }

    @Transactional
    public User login(UserLoginDTO userLoginDTO) throws UnauthorizedException, EntityNotFoundException {

        User user = this.userRepository.findByEmailIgnoreCase(userLoginDTO.email);

        if (Objects.isNull(user))
            throw new EntityNotFoundException();

        if (!BCryptUtils.matches(userLoginDTO.password, user.getPassword()))
            throw new UnauthorizedException();

        this.updateLastLogin(user);

        return user;
    }

    private void updateLastLogin(User user){
        user.setLastLogin(new Date());
        this.userRepository.save(user);
    }
}
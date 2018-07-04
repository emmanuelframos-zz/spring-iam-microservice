package com.iam.service;

import com.config.SessionConfig;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private SessionConfig sessionConfig;

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
            throw new UnauthorizedException(ExceptionMessages.INVALID_USER_OR_PASSWORD);

        this.updateLastLogin(user);

        return user;
    }

    private void updateLastLogin(User user){
        user.setLastLogin(new Date());
        this.userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User profile(String token, String userUUID) throws UnauthorizedException, BusinessException {
        User user = this.userRepository.findByUuid(UUID.fromString(userUUID));

        if (Objects.isNull(user))
            throw new BusinessException(ExceptionMessages.USER_NOT_FOUND);

        if (tokenAreEquals(user, token)){
            checkIfSessionIsctive(user);
        }else {
            throw new UnauthorizedException(ExceptionMessages.UNAUTHORIZED);
        }

        return user;
    }

    private boolean tokenAreEquals(User user, String token){
        return user.getToken().equals(token);
    }

    private void checkIfSessionIsctive(User user) throws UnauthorizedException {
        LocalDateTime lastLogin = new java.sql.Timestamp(user.getLastLogin().getTime()).toLocalDateTime();
        LocalDateTime now = LocalDateTime.now();

        Duration duration = Duration.between(lastLogin, now);
        long minutes = Math.abs(duration.toMinutes());

        if (minutes > sessionConfig.getSessionTime())
            throw new UnauthorizedException(ExceptionMessages.INVALID_SESSION);
    }
}
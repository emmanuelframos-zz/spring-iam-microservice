package com.iam.validator;

import com.exception.BusinessException;
import com.exception.BusinessRuntimeException;
import com.exception.ExceptionMessages;
import com.iam.api.v1.dto.PhoneDTO;
import com.iam.api.v1.dto.UserDTO;
import com.iam.api.v1.dto.UserLoginDTO;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserValidator {

    private static final Integer PASSWORD_LENGTH = 8;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public void validateOnSignup(UserDTO userDTO) throws BusinessException {
        validateName(userDTO.name);
        validateEmail(userDTO.email);
        validatePassword(userDTO.password);
        validatePhones(userDTO.phones);
    }

    public void validateOnLogin(UserLoginDTO userLoginDTO) throws BusinessException {
        validateEmail(userLoginDTO.email);
        validatePassword(userLoginDTO.password);
    }

    private void validateName(String name) throws BusinessException {
        if (StringUtils.isEmpty(name))
            throw new BusinessException(ExceptionMessages.USER_NAME_MANDATORY);
    }

    private void validateEmail(String email) throws BusinessException {
        if (StringUtils.isEmpty(email))
            throw new BusinessException(ExceptionMessages.USER_EMAIL_MANDATORY);

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
        if (!matcher.find())
            throw new BusinessException(ExceptionMessages.USER_EMAIL_INVALID);
    }

    private void validatePassword(String password) throws BusinessException {
        if (StringUtils.isEmpty(password))
            throw new BusinessException(ExceptionMessages.USER_PASSWORD_MANDATORY);

        if (password.length() < PASSWORD_LENGTH)
            throw new BusinessException(ExceptionMessages.USER_PASSWORD_TOO_SMALL);
    }

    private void validatePhones(List<PhoneDTO> phones) throws BusinessException {
        if (CollectionUtils.isEmpty(phones))
            throw new BusinessException(ExceptionMessages.USER_PHONE_MANDATORY);

        phones.forEach(p -> {
            if (StringUtils.isEmpty(p.ddd))
                throw new BusinessRuntimeException(ExceptionMessages.PHONE_DDD_MANDATORY);

            if (StringUtils.isEmpty(p.number))
                throw new BusinessRuntimeException(ExceptionMessages.PHONE_NUMBER_MANDATORY);
        });
    }

    public void validateProfile(String token) throws BusinessException {
        validateToken(token);
    }

    private void validateToken(String token) throws BusinessException {
        if (StringUtils.isEmpty(token))
            throw new BusinessException(ExceptionMessages.UNAUTHORIZED);
    }
}
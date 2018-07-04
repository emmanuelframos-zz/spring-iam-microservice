package com.iam.validator;

import com.exception.BusinessException;
import com.exception.BusinessRuntimeException;
import com.exception.ExceptionMessages;
import com.iam.api.v1.dto.UserDTO;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class UserValidator {

    public void validateOnSignup(UserDTO userDTO) throws BusinessException {
        validateName(userDTO);
        validateEmail(userDTO);
        validatePassword(userDTO);
        validatePhones(userDTO);
    }

    private void validateName(UserDTO userDTO) throws BusinessException {
        if (StringUtils.isEmpty(userDTO.name))
            throw new BusinessException(ExceptionMessages.USER_NAME_MANDATORY);
    }

    private void validateEmail(UserDTO userDTO) throws BusinessException {
        if (StringUtils.isEmpty(userDTO.email))
            throw new BusinessException(ExceptionMessages.USER_EMAIL_MANDATORY);
    }

    private void validatePassword(UserDTO userDTO) throws BusinessException {
        if (StringUtils.isEmpty(userDTO.password))
            throw new BusinessException(ExceptionMessages.USER_PASSWORD_MANDATORY);
    }

    private void validatePhones(UserDTO userDTO) throws BusinessException {
        if (CollectionUtils.isEmpty(userDTO.phones))
            throw new BusinessException(ExceptionMessages.USER_PHONE_MANDATORY);

        userDTO.phones.forEach(p -> {
            if (StringUtils.isEmpty(p.ddd))
                throw new BusinessRuntimeException(ExceptionMessages.PHONE_DDD_MANDATORY);

            if (StringUtils.isEmpty(p.number))
                throw new BusinessRuntimeException(ExceptionMessages.PHONE_NUMBER_MANDATORY);
        });
    }
}
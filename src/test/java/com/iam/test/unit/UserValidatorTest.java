package com.iam.test.unit;

import com.exception.BusinessException;
import com.exception.BusinessRuntimeException;
import com.exception.ExceptionMessages;
import com.iam.api.v1.dto.PhoneDTO;
import com.iam.api.v1.dto.UserDTO;
import com.iam.api.v1.dto.UserLoginDTO;
import com.iam.validator.UserValidator;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;

@RunWith(SpringRunner.class)
public class UserValidatorTest {

    @Spy
    private UserValidator userValidator;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void validateOnSignupOk() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.name = "Dummy Name";
        userDTO.email = "dummy@email.com";
        userDTO.password = "dummypassword";

        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.ddd = "22";
        phoneDTO.number = "34345656";

        userDTO.phones = Arrays.asList(phoneDTO);

        userValidator.validateOnSignup(userDTO);
    }

    @Test
    public void validateOnSignup_NameNOk() throws Exception {

        expectedException.expect(BusinessException.class);
        expectedException.expectMessage(ExceptionMessages.USER_NAME_MANDATORY.getMessage());

        UserDTO userDTO = new UserDTO();
        userDTO.email = "dummy@email.com";
        userDTO.password = "dummypassword";

        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.ddd = "22";
        phoneDTO.number = "34345656";

        userDTO.phones = Arrays.asList(phoneDTO);

        userValidator.validateOnSignup(userDTO);
    }

    @Test
    public void validateOnSignup_EmailNOk() throws Exception {

        expectedException.expect(BusinessException.class);
        expectedException.expectMessage(ExceptionMessages.USER_EMAIL_MANDATORY.getMessage());

        UserDTO userDTO = new UserDTO();
        userDTO.name = "Dummy Name";
        userDTO.password = "dummypassword";

        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.ddd = "22";
        phoneDTO.number = "34345656";

        userDTO.phones = Arrays.asList(phoneDTO);

        userValidator.validateOnSignup(userDTO);
    }

    @Test
    public void validateOnSignup_PasswordNOk() throws Exception {

        expectedException.expect(BusinessException.class);
        expectedException.expectMessage(ExceptionMessages.USER_PASSWORD_MANDATORY.getMessage());

        UserDTO userDTO = new UserDTO();
        userDTO.name = "Dummy Name";
        userDTO.email = "dummy@email.com";

        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.ddd = "22";
        phoneDTO.number = "34345656";

        userDTO.phones = Arrays.asList(phoneDTO);

        userValidator.validateOnSignup(userDTO);
    }

    @Test
    public void validateOnSignup_PhonesEmptyNOk() throws Exception {

        expectedException.expect(BusinessException.class);
        expectedException.expectMessage(ExceptionMessages.USER_PHONE_MANDATORY.getMessage());

        UserDTO userDTO = new UserDTO();
        userDTO.name = "Dummy Name";
        userDTO.password = "dummypassword";
        userDTO.email = "dummy@email.com";

        userDTO.phones = new ArrayList<>();

        userValidator.validateOnSignup(userDTO);
    }

    @Test
    public void validateOnSignup_PhonesDDDNOk() throws Exception {

        expectedException.expect(BusinessRuntimeException.class);
        expectedException.expectMessage(ExceptionMessages.PHONE_DDD_MANDATORY.getMessage());

        UserDTO userDTO = new UserDTO();
        userDTO.name = "Dummy Name";
        userDTO.password = "dummypassword";
        userDTO.email = "dummy@email.com";

        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.number = "34345656";

        userDTO.phones = Arrays.asList(phoneDTO);

        userValidator.validateOnSignup(userDTO);
    }

    @Test
    public void validateOnSignup_PhonesNumberNOk() throws Exception {

        expectedException.expect(BusinessRuntimeException.class);
        expectedException.expectMessage(ExceptionMessages.PHONE_NUMBER_MANDATORY.getMessage());

        UserDTO userDTO = new UserDTO();
        userDTO.name = "Dummy Name";
        userDTO.password = "dummypassword";
        userDTO.email = "dummy@email.com";

        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.ddd = "22";

        userDTO.phones = Arrays.asList(phoneDTO);

        userValidator.validateOnSignup(userDTO);
    }

    @Test
    public void validateOnLoginOk() throws Exception {
        UserLoginDTO userDTO = new UserLoginDTO();
        userDTO.email = "dummy@email.com";
        userDTO.password = "dummypassword";

        userValidator.validateOnLogin(userDTO);
    }

    @Test
    public void validateOnLogin_EmailNOk() throws Exception {

        expectedException.expect(BusinessException.class);
        expectedException.expectMessage(ExceptionMessages.USER_EMAIL_MANDATORY.getMessage());

        UserLoginDTO userDTO = new UserLoginDTO();
        userDTO.password = "dummypassword";

        userValidator.validateOnLogin(userDTO);
    }

    @Test
    public void validateOnLogin_PasswordNOk() throws Exception {

        expectedException.expect(BusinessException.class);
        expectedException.expectMessage(ExceptionMessages.USER_PASSWORD_MANDATORY.getMessage());

        UserLoginDTO userDTO = new UserLoginDTO();
        userDTO.email = "dummy@email.com";

        userValidator.validateOnLogin(userDTO);
    }

    @Test
    public void validateProfileOk() throws Exception {
        userValidator.validateProfile("token");
    }

    @Test
    public void validateProfileNOk() throws Exception {

        expectedException.expect(BusinessException.class);
        expectedException.expectMessage(ExceptionMessages.UNAUTHORIZED.getMessage());

        userValidator.validateProfile(null);
    }
}
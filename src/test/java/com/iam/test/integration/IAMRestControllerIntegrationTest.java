package com.iam.test.integration;

import com.Application;
import com.commons.dto.ErrorDTO;
import com.exception.ExceptionMessages;
import com.iam.api.v1.dto.PhoneDTO;
import com.iam.api.v1.dto.UserDTO;
import com.iam.api.v1.dto.UserLoginDTO;
import com.iam.domain.User;
import com.iam.parser.UserParser;
import com.iam.repository.UserRepository;
import com.iam.test.commons.RestRequest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class IAMRestControllerIntegrationTest {

    @Value("${local.server.port}")
    private Integer serverPort;

    private String getBaseURl(){
        return String.format("http://localhost:%d/api/v1/iam", serverPort);
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserParser userParser;

    @Test
    public void testSignupIntegrationOk(){

        UserDTO userDTO = new UserDTO();
        userDTO.name = "Dummy Name";
        userDTO.email = "dummy@email.com";
        userDTO.password = "dummypassword";

        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.ddd = "22";
        phoneDTO.number = "34345656";

        userDTO.phones = Arrays.asList(phoneDTO);

        ResponseEntity<UserDTO> response = RestRequest
                .build()
                .baseUrl(getBaseURl())
                .endpoint("/signup")
                .method(HttpMethod.POST)
                .payload(userDTO)
                .execute(UserDTO.class);

        UserDTO userCreated = response.getBody();

        Assert.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        Assert.assertNotNull(userCreated.id);
        Assert.assertNotNull(userCreated.created);
        Assert.assertNotNull(userCreated.lastLogin);
        Assert.assertNotNull(userCreated.lastModified);
        Assert.assertNotNull(userCreated.token);
    }

    @Test
    public void testLoginIntegrationOk(){

        UserDTO userDTO = new UserDTO();
        userDTO.name = "Dummy Name";
        userDTO.email = "dummy.1@email.com";
        userDTO.password = "dummypassword";

        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.ddd = "22";
        phoneDTO.number = "34345656";

        userDTO.phones = Arrays.asList(phoneDTO);

        RestRequest
                .build()
                .baseUrl(getBaseURl())
                .endpoint("/signup")
                .method(HttpMethod.POST)
                .payload(userDTO)
                .execute(UserDTO.class);

        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.email = "dummy.1@email.com";
        userLoginDTO.password = "dummypassword";

        ResponseEntity<UserDTO> response = RestRequest
                .build()
                .baseUrl(getBaseURl())
                .endpoint("/login")
                .method(HttpMethod.POST)
                .payload(userLoginDTO)
                .execute(UserDTO.class);

        UserDTO userLogged = response.getBody();

        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertNotNull(userLogged.id);
        Assert.assertNotNull(userLogged.created);
        Assert.assertNotNull(userLogged.lastLogin);
        Assert.assertNotNull(userLogged.lastModified);
        Assert.assertNotNull(userLogged.token);
    }

    @Test
    public void testProfileOk(){

        UserDTO userDTO = new UserDTO();
        userDTO.name = "Dummy Name";
        userDTO.email = "dummy.2@email.com";
        userDTO.password = "dummypassword";

        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.ddd = "22";
        phoneDTO.number = "34345656";

        userDTO.phones = Arrays.asList(phoneDTO);

        ResponseEntity<UserDTO> response = RestRequest
                .build()
                .baseUrl(getBaseURl())
                .endpoint("/signup")
                .method(HttpMethod.POST)
                .payload(userDTO)
                .execute(UserDTO.class);

        UserDTO userCreated = response.getBody();

        response = RestRequest
                .build()
                .baseUrl(getBaseURl())
                .addHeader(HttpHeaders.AUTHORIZATION, userCreated.token)
                .endpoint("/profile/" + userCreated.id)
                .method(HttpMethod.GET)
                .execute(UserDTO.class);

        UserDTO userLogged = response.getBody();

        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertNotNull(userLogged.id);
        Assert.assertNotNull(userLogged.created);
        Assert.assertNotNull(userLogged.lastLogin);
        Assert.assertNotNull(userLogged.lastModified);
        Assert.assertNotNull(userLogged.token);
    }

    @Test
    public void testSignupNOk(){
        UserDTO userDTO = new UserDTO();
        userDTO.name = "Dummy Name";
        userDTO.email = "dummy.3@email.com";
        userDTO.password = "dummypassword";

        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.ddd = "22";
        phoneDTO.number = "34345656";

        userDTO.phones = Arrays.asList(phoneDTO);

        ResponseEntity<UserDTO> response = RestRequest
                .build()
                .baseUrl(getBaseURl())
                .endpoint("/signup")
                .method(HttpMethod.POST)
                .payload(userDTO)
                .execute(UserDTO.class);

        userDTO = new UserDTO();
        userDTO.name = "Dummy Name";
        userDTO.email = "dummy.3@email.com";
        userDTO.password = "dummypassword";

        phoneDTO = new PhoneDTO();
        phoneDTO.ddd = "22";
        phoneDTO.number = "34345656";

        userDTO.phones = Arrays.asList(phoneDTO);

        ResponseEntity<ErrorDTO> responseFail = RestRequest
                .build()
                .baseUrl(getBaseURl())
                .endpoint("/signup")
                .method(HttpMethod.POST)
                .payload(userDTO)
                .execute(ErrorDTO.class);

        ErrorDTO errorDTO = responseFail.getBody();

        Assert.assertEquals(responseFail.getStatusCode(), HttpStatus.BAD_REQUEST);
        Assert.assertEquals(errorDTO.getMessage(), ExceptionMessages.EMAIL_ALREADY_EXISTS.getMessage());
    }

    @Test
    public void testLogin_EmailNOk(){
        UserDTO userDTO = new UserDTO();
        userDTO.name = "Dummy Name";
        userDTO.email = "dummy.3@email.com";
        userDTO.password = "dummypassword";

        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.ddd = "22";
        phoneDTO.number = "34345656";

        userDTO.phones = Arrays.asList(phoneDTO);

        ResponseEntity<UserDTO> response = RestRequest
                .build()
                .baseUrl(getBaseURl())
                .endpoint("/signup")
                .method(HttpMethod.POST)
                .payload(userDTO)
                .execute(UserDTO.class);

        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.email = "dummy.4@email.com";
        userLoginDTO.password = "dummypassword";

        ResponseEntity<ErrorDTO> responseFail = RestRequest
                .build()
                .baseUrl(getBaseURl())
                .endpoint("/login")
                .method(HttpMethod.POST)
                .payload(userLoginDTO)
                .execute(ErrorDTO.class);

        ErrorDTO errorDTO = responseFail.getBody();

        Assert.assertEquals(responseFail.getStatusCode(), HttpStatus.NOT_FOUND);
        Assert.assertEquals(errorDTO.getMessage(), ExceptionMessages.INVALID_USER_OR_PASSWORD.getMessage());
    }

    @Test
    public void testLogin_PasswordNOk(){
        UserDTO userDTO = new UserDTO();
        userDTO.name = "Dummy Name";
        userDTO.email = "dummy.3@email.com";
        userDTO.password = "dummypassword";

        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.ddd = "22";
        phoneDTO.number = "34345656";

        userDTO.phones = Arrays.asList(phoneDTO);

        ResponseEntity<UserDTO> response = RestRequest
                .build()
                .baseUrl(getBaseURl())
                .endpoint("/signup")
                .method(HttpMethod.POST)
                .payload(userDTO)
                .execute(UserDTO.class);

        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.email = "dummy.3@email.com";
        userLoginDTO.password = "dummypassword1";

        ResponseEntity<ErrorDTO> responseFail = RestRequest
                .build()
                .baseUrl(getBaseURl())
                .endpoint("/login")
                .method(HttpMethod.POST)
                .payload(userLoginDTO)
                .execute(ErrorDTO.class);

        ErrorDTO errorDTO = responseFail.getBody();

        Assert.assertEquals(responseFail.getStatusCode(), HttpStatus.UNAUTHORIZED);
        Assert.assertEquals(errorDTO.getMessage(), ExceptionMessages.INVALID_USER_OR_PASSWORD.getMessage());
    }

    @Test
    public void testProfile_TokenNotSentNOk(){

        UserDTO userDTO = new UserDTO();
        userDTO.name = "Dummy Name";
        userDTO.email = "dummy.6@email.com";
        userDTO.password = "dummypassword";

        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.ddd = "22";
        phoneDTO.number = "34345656";

        userDTO.phones = Arrays.asList(phoneDTO);

        ResponseEntity<UserDTO> response = RestRequest
                .build()
                .baseUrl(getBaseURl())
                .endpoint("/signup")
                .method(HttpMethod.POST)
                .payload(userDTO)
                .execute(UserDTO.class);

        UserDTO userCreated = response.getBody();

        ResponseEntity<ErrorDTO> responseFail = RestRequest
                .build()
                .baseUrl(getBaseURl())
                .endpoint("/profile/" + userCreated.id)
                .method(HttpMethod.GET)
                .execute(ErrorDTO.class);

        ErrorDTO errorDTO = responseFail.getBody();

        Assert.assertEquals(responseFail.getStatusCode(), HttpStatus.BAD_REQUEST);
        Assert.assertEquals(errorDTO.getMessage(), ExceptionMessages.UNAUTHORIZED.getMessage());
    }

    @Test
    public void testProfile_TokenDoesNotMatchesNOk() {

        UserDTO userDTO = new UserDTO();
        userDTO.name = "Dummy Name";
        userDTO.email = "dummy.5@email.com";
        userDTO.password = "dummypassword";

        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.ddd = "22";
        phoneDTO.number = "34345656";

        userDTO.phones = Arrays.asList(phoneDTO);

        ResponseEntity<UserDTO> response = RestRequest
                .build()
                .baseUrl(getBaseURl())
                .endpoint("/signup")
                .method(HttpMethod.POST)
                .payload(userDTO)
                .execute(UserDTO.class);

        UserDTO userCreated = response.getBody();

        ResponseEntity<ErrorDTO> responseFail = RestRequest
                .build()
                .baseUrl(getBaseURl())
                .endpoint("/profile/" + userCreated.id)
                .addHeader(HttpHeaders.AUTHORIZATION, "dummytoken")
                .method(HttpMethod.GET)
                .execute(ErrorDTO.class);

        ErrorDTO errorDTO = responseFail.getBody();

        Assert.assertEquals(responseFail.getStatusCode(), HttpStatus.UNAUTHORIZED);
        Assert.assertEquals(errorDTO.getMessage(), ExceptionMessages.UNAUTHORIZED.getMessage());
    }

    @Test
    public void testProfile_TokenSessionInvalidateNOk() {

        UserDTO userDTO = new UserDTO();
        userDTO.name = "Dummy Name";
        userDTO.email = "dummy.7@email.com";
        userDTO.password = "dummypassword";

        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.ddd = "22";
        phoneDTO.number = "34345656";

        userDTO.phones = Arrays.asList(phoneDTO);

        ResponseEntity<UserDTO> response = RestRequest
                .build()
                .baseUrl(getBaseURl())
                .endpoint("/signup")
                .method(HttpMethod.POST)
                .payload(userDTO)
                .execute(UserDTO.class);

        UserDTO userCreated = response.getBody();

        User user = userRepository.findByUuid(UUID.fromString(userCreated.id));

        LocalDateTime sessionExpired = LocalDateTime.now().plusMinutes(31);

        Date dateConverted = java.util.Date
                .from(sessionExpired.atZone(ZoneId.systemDefault())
                        .toInstant());

        user.setLastLogin(dateConverted);

        userRepository.save(user);

        ResponseEntity<ErrorDTO> responseFail = RestRequest
                .build()
                .baseUrl(getBaseURl())
                .endpoint("/profile/" + userCreated.id)
                .addHeader(HttpHeaders.AUTHORIZATION, userCreated.token)
                .method(HttpMethod.GET)
                .execute(ErrorDTO.class);

        ErrorDTO errorDTO = responseFail.getBody();

        Assert.assertEquals(responseFail.getStatusCode(), HttpStatus.UNAUTHORIZED);
        Assert.assertEquals(errorDTO.getMessage(), ExceptionMessages.INVALID_SESSION.getMessage());
    }
}
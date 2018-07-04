package com.iam.test.integration;

import com.Application;
import com.iam.api.v1.dto.PhoneDTO;
import com.iam.api.v1.dto.UserDTO;
import com.iam.api.v1.dto.UserLoginDTO;
import com.iam.test.commons.RestRequest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class IAMRestControllerIntegrationTest {

    @Value("${local.server.port}")
    private Integer serverPort;

    private String getBaseURl(){
        return String.format("http://localhost:%d/api/v1/iam", serverPort);
    }

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
}
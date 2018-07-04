package com.iam.test.contract;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iam.api.v1.IAMRestController;
import com.iam.api.v1.dto.PhoneDTO;
import com.iam.api.v1.dto.UserDTO;
import com.iam.api.v1.dto.UserLoginDTO;
import com.iam.parser.UserParser;
import com.iam.service.UserService;
import com.iam.validator.UserValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.PostConstruct;
import java.util.Arrays;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class IAMRestControllerContractTest {

    private MockMvc mockMvc;

    @InjectMocks
    private IAMRestController iamRestController;

    @Mock
    private UserValidator userValidator;

    @Mock
    private UserParser userParser;

    @Mock
    private UserService userService;

    @PostConstruct
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(iamRestController).build();
    }

    @Test
    public void testSignupContractOk() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        UserDTO userDTO = new UserDTO();
        userDTO.name = "Dummy Name";
        userDTO.email = "dummy@email.com";
        userDTO.password = "dummypassword";

        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.ddd = "22";
        phoneDTO.number = "34345656";

        userDTO.phones = Arrays.asList(phoneDTO);

        mockMvc.perform(post("/api/v1/iam/signup")
            .contentType(APPLICATION_JSON_UTF8)
            .content(mapper.writeValueAsString(userDTO)))
            .andExpect(status().isCreated());
    }

    @Test
    public void testLoginContractOk() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        UserLoginDTO userDTO = new UserLoginDTO();
        userDTO.email = "dummy@email.com";
        userDTO.password = "dummypassword";

        mockMvc.perform(post("/api/v1/iam/login")
            .contentType(APPLICATION_JSON_UTF8)
            .content(mapper.writeValueAsString(userDTO)))
            .andExpect(status().isOk());
    }

    @Test
    public void testProfileContractOk() throws Exception {

        mockMvc.perform(get("/api/v1/iam/profile/1")
                .header(HttpHeaders.AUTHORIZATION, "token"))
                .andExpect(status().isOk());
    }
}
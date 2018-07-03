package com.iam.test.contract;

import com.iam.api.v1.IAMRestController;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.PostConstruct;

public class IAMRestControllerContractTest {

    private MockMvc mockMvc;

    @InjectMocks
    private IAMRestController iamRestController;

    @PostConstruct
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(iamRestController).build();
    }

    @Test
    public void testOk(){
        Assert.assertEquals(1L, 1L);
    }
}
package com.iam.test.integration;

import com.iam.Application;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class IAMRestControllerIntegrationTest {

    @Test
    public void testOk(){
        Assert.assertEquals(1L, 1L);
    }
}
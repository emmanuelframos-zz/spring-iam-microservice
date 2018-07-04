package com.iam.test.unit;

import com.utils.BCryptUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class BCryptUtilsTest {

    @Test
    public void testMatchesOk(){
        String hashed = BCryptUtils.hash("p4ssw0rd", 12);
        Assert.assertTrue(BCryptUtils.matches("p4ssw0rd", hashed));
    }

    @Test
    public void testMatchesNOk(){
        String hashed = BCryptUtils.hash("p4ssw0rd", 12);
        Assert.assertFalse(BCryptUtils.matches("p4ssword", hashed));
    }
}
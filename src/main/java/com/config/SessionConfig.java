package com.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionConfig {

    @Value("${session.time}")
    private Integer sessionTime;

    public Integer getSessionTime() {
        return sessionTime;
    }
}

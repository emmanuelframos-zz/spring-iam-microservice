package com.iam.test.commons;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public final class RestTemplateBuilder {

    public static RestTemplate build() {
        RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
        return restTemplate;
    }

    private static ClientHttpRequestFactory getClientHttpRequestFactory() {

        CloseableHttpClient client = HttpClientBuilder
                .create()
                .build();

        return new HttpComponentsClientHttpRequestFactory(client);
    }
}
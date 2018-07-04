package com.iam.test.commons;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;

import java.io.File;

public class RestRequest<E> {

    private String baseUrl;

    private String endpoint;

    private HttpMethod method;

    private HttpHeaders headers;

    private E payload;

    private RestRequest(){
        this.headers = new HttpHeaders();
        this.baseUrl = StringUtils.EMPTY;
        this.endpoint = StringUtils.EMPTY;
    }

    public static RestRequest build(){
        return new RestRequest();
    }

    public RestRequest baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public RestRequest method(HttpMethod method) {
        this.method = method;
        return this;
    }

    public RestRequest endpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public RestRequest payload(E payload) {
        this.payload = payload;
        return this;
    }

    public RestRequest addHeader(String name, String value) {
        if (this.headers == null)
            this.headers = new HttpHeaders();

        this.headers.add(name, value);

        return this;
    }

    public ResponseEntity execute() {

        HttpEntity entity = (payload == null)
                ? new HttpEntity<Void>(headers)
                : new HttpEntity<>(payload, headers);

        String requestUrl = this.baseUrl + endpoint;
        try {
            return RestTemplateBuilder.build().exchange(requestUrl, method, entity, new ParameterizedTypeReference<E>(){});
        } catch (HttpClientErrorException e) {
            return new ResponseEntity(e.getStatusCode());
        }
    }

    public <E> ResponseEntity<E> execute(Class<E> responseType) {

        HttpEntity entity = (payload == null)
                ? new HttpEntity<Void>(headers)
                : new HttpEntity<>(payload, headers);

        String requestUrl = this.baseUrl + endpoint;
        try {
            return RestTemplateBuilder.build().exchange(requestUrl, method, entity, responseType);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getStatusCode());
        }
    }

    public <C> ResponseEntity<C> execute(ParameterizedTypeReference<C> responseType) {

        HttpEntity entity = (payload == null)
                ? new HttpEntity<Void>(headers)
                : new HttpEntity<>(payload, headers);

        String requestUrl = this.baseUrl + endpoint;
        try {
            return RestTemplateBuilder.build().exchange(requestUrl, method, entity, responseType);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getStatusCode());
        }
    }

    public <C> ResponseEntity<C> sendFileMultipartFormData(ParameterizedTypeReference<C> responseType, File file, String metaData) {

        LinkedMultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("file", new FileSystemResource(file));
        params.add("meta-data", metaData);

        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity =
                new HttpEntity<>(params, headers);

        String requestUrl = this.baseUrl + endpoint;

        return RestTemplateBuilder.build().exchange(
                requestUrl,
                HttpMethod.POST,
                requestEntity,
                responseType);
    }

}
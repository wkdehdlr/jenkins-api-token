package com.example.demo.jenkins;

import java.net.URI;
import java.util.Collections;
import java.util.Optional;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class JenkinsApi {

    private URI getUri(String path, Object... pathParams) {
        return UriComponentsBuilder.newInstance()
            .scheme("http")
            .host("localhost")
            .port("8080")
            .path(path)
            .build()
            .expand(Optional.ofNullable(pathParams)
                .orElseGet(Collections.EMPTY_LIST::toArray))
            .encode()
            .toUri();
    }

    public String callJenkinsRestApiNotAuth(String job) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
            getUri("/job/{job}/lastStableBuild/api/json", job),
            HttpMethod.GET,
            null,
            String.class
        ).getBody();
    }

    public String callJenkinsRestApiIncludeAuth(String job) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin", "90c355a3db441303cbaf434606ae05ce");
        HttpEntity<String> httpEntity = new HttpEntity<>("parameters", headers);
        return restTemplate.exchange(
            getUri("/job/{job}/lastStableBuild/api/json", job),
            HttpMethod.GET,
            httpEntity,
            String.class
        ).getBody();
    }
}

package com.example.demo.jenkins;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;

@SpringBootTest
class JenkinsApiTest {

    @Autowired
    private JenkinsApi jenkinsApi;

    @Test
    void 권한이_없음() {
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
            () -> jenkinsApi.callJenkinsRestApiNotAuth("application_build"));
        assertEquals(403, exception.getRawStatusCode());
    }

    @Test
    void API_Token을_추가하면_정상호출() {
        assertDoesNotThrow(() -> jenkinsApi.callJenkinsRestApiIncludeAuth("application_build"));
    }
}
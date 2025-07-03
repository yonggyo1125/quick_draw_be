package org.koreait.predict.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TruncateServiceTest {
    @Autowired
    private TruncateService service;

    @Test
    void test() {
        service.process();
    }
}

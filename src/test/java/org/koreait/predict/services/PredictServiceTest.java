package org.koreait.predict.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

@SpringBootTest
public class PredictServiceTest {

    @Autowired
    private PredictService service;


    @Test
    void test() throws Exception {
        InputStream in = new BufferedInputStream(new FileInputStream("C:/quick_draw/sample.jpg"));

        MockMultipartFile file = new MockMultipartFile("file", "sample.jpg", "image/jpeg", in);
        service.process(file);
    }
}

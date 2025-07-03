package org.koreait.predict.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class PredictServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PredictService service;

    private MockMultipartFile file;

    @BeforeEach
    void init() throws Exception {
        InputStream in = new BufferedInputStream(new FileInputStream("C:/quick_draw/sample.jpg"));

        file = new MockMultipartFile("file", "sample.jpg", "image/jpeg", in);
    }

    @Test
    void test() throws Exception {

        List<String[]> items = service.process(file);
        items.forEach(item -> System.out.println(Arrays.toString(item)));
    }

    @Test
    void test2() throws Exception{
        mockMvc.perform(multipart("/quickdraw/predict")
                .file(file))
                .andDo(print());
    }
}

package org.koreait.predict.controllers;

import lombok.RequiredArgsConstructor;
import org.koreait.predict.services.PredictService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quickdraw/predict")
public class PredictController {

    private final PredictService service;

    @PostMapping
    public List<String[]> predict(@RequestPart("image") MultipartFile image) {
        return service.process(image);
    }
}

package org.koreait.predict.controllers;

import lombok.RequiredArgsConstructor;
import org.koreait.predict.services.PredictService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins="*")
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

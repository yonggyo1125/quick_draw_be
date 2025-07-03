package org.koreait.predict.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Lazy
@Service
@RequiredArgsConstructor
public class PredictService {

    public List<String[]> process(MultipartFile image) {

        return null;
    }
}

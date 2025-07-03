package org.koreait.predict.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.koreait.global.configs.FileProperties;
import org.koreait.global.configs.PythonProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Lazy
@Service
@RequiredArgsConstructor
@EnableConfigurationProperties({FileProperties.class, PythonProperties.class})
public class PredictService {

    private final FileProperties fileProperties;
    private final PythonProperties pythonProperties;
    private final WebApplicationContext ctx;

    public List<String[]> process(MultipartFile image) {

        try {
            String imagePath = uploadImage(image);


            boolean isProduction = Arrays.stream(ctx.getEnvironment().getActiveProfiles()).anyMatch(s -> s.equals("prod") || s.equals("mac"));

            String activationCommand = null, pythonPath = null;
            if (isProduction) { // 리눅스 환경, 서비스 환경
                activationCommand = String.format("%s/activate", pythonProperties.getBase());
                pythonPath = pythonProperties.getBase() + "/python";
            } else { // 윈도우즈 환경
                activationCommand = String.format("%s/activate.bat", pythonProperties.getBase());
                pythonPath = pythonProperties.getBase() + "/python.exe";
            }

            ProcessBuilder builder = isProduction ? new ProcessBuilder("/bin/sh", activationCommand) : new ProcessBuilder(activationCommand); // 가상환경 활성화
            Process process = builder.start();
            if (process.waitFor() == 0) { // 정상 수행된 경우
                builder = new ProcessBuilder(pythonPath, properties.getTrend() + "/trend.py", fileProperties.getPath() + "/trend", url);
                process = builder.start();
                int statusCode = process.waitFor();
                if (statusCode == 0) {
                    String json = process.inputReader().lines().collect(Collectors.joining());
                    CollectedTrend item = om.readValue(json, CollectedTrend.class);

                    String wordCloud = String.format("%s/trend/%s", fileProperties.getUrl(), item.getImage());
                    try {
                        String keywords = om.writeValueAsString(item.getKeywords());
                        Trend data = new Trend();
                        data.setCategory(url.contains("news.naver.com") ? "NEWS" : "" + Objects.hash(url));
                        data.setWordCloud(wordCloud);
                        data.setKeywords(keywords);
                        repository.save(data);
                    } catch (JsonProcessingException e) {}

                } else {
                    System.out.println("statusCode:" + statusCode);
                    process.errorReader().lines().forEach(System.out::println);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return List.of();
    }

    /**
     * 이미지 업로드
     * @param image
     * @return
     */
    private String uploadImage(MultipartFile image) throws IOException {
        String uploadPath = String.format("%s/%s_%s", fileProperties.getPath(), System.currentTimeMillis(), image.getOriginalFilename());
        File file = new File(uploadPath);
        image.transferTo(file);

        return uploadPath;
    }
}

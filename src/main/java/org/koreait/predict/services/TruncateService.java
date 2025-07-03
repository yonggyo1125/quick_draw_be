package org.koreait.predict.services;

import lombok.RequiredArgsConstructor;
import org.koreait.global.configs.FileProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(FileProperties.class)
public class TruncateService {

    private final FileProperties properties;

    @Scheduled(cron="0 0 0 * * *")
    public void process() {
        File dir = new File(properties.getPath());
        if (!dir.exists() || !dir.isDirectory()) return;
        File[] files = dir.listFiles(File::isFile);
        if (files != null) Arrays.stream(files).forEach(File::delete);
    }
}

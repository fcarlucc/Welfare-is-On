package com.imageservice.app.Initializer;

import com.imageservice.app.model.Image;
import org.springframework.mock.web.MockMultipartFile;
import com.imageservice.app.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    @Value("${upload.dir}")
    private String uploadDir;

    private final ImageService imageService;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            String path = uploadDir + "/LeonardoSample.png";
            File realFile = new File(path);
            FileInputStream input = new FileInputStream(realFile);
            MultipartFile file = new MockMultipartFile(
                    "file",               // Name of the file parameter in the request
                    realFile.getName(),       // Original file name
                    "image/png",          // Content type
                    input                 // Input stream of the file
            );
            Image image = new Image();
            image.setName(file.getOriginalFilename());
            image.setPath(path);
            image.setType(file.getContentType());
            image.setSize(file.getSize());
            imageService.create(image);
            path = uploadDir + "/coach.jpg";
            realFile = new File(path);
            input = new FileInputStream(realFile);
            file = new MockMultipartFile(
                    "file",               // Name of the file parameter in the request
                    realFile.getName(),       // Original file name
                    "image/png",          // Content type
                    input                 // Input stream of the file
            );
            Image imageCoach = new Image();
            imageCoach.setName(file.getOriginalFilename());
            imageCoach.setPath(path);
            imageCoach.setType(file.getContentType());
            imageCoach.setSize(file.getSize());
            imageService.create(imageCoach);
        };
    }
}

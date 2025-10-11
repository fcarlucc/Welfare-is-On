package com.imageservice.app.controller;

import com.imageservice.app.dto.ResponseDto;
import com.imageservice.app.service.ImageService;
import com.imageservice.app.validator.ImageValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/image")
public class ImageController {

    private final ImageService imageService;
    private final ImageValidator imageValidator;

    /**
     * Handles HTTP POST requests to upload an image file.
     *
     * @param file The image file to upload.
     * @return ResponseEntity containing the ResponseDto with the result of the upload operation.
     */
    @PostMapping("/upload")
    public ResponseEntity<ResponseDto> handleFileUpload(@RequestParam("file") MultipartFile file) {
        return imageService.uploadImage(file);
    }

    /**
     * Handles HTTP GET requests to retrieve an image by its ID.
     *
     * @param id The ID of the image to retrieve.
     * @return ResponseEntity containing a byte array representing the image content.
     * @throws IOException If there is an error retrieving the image.
     */
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) throws IOException {
        return imageService.getImageById(id);
    }
}

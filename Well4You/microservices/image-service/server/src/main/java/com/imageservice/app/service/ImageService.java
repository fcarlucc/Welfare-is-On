package com.imageservice.app.service;

import com.imageservice.app.dto.ResponseDto;
import com.imageservice.app.exception.ImageNotFoundException;
import com.imageservice.app.model.Image;
import com.imageservice.app.repository.IImageRepository;
import com.imageservice.app.validator.ImageValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

/**
 * Service class for managing image operations.
 */
@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${upload.dir}")
    private String uploadDir;

    private final IImageRepository imageRepository;
    private final ImageValidator imageValidator;

    /**
     * Retrieves an image by its ID.
     * @param id The ID of the image to retrieve.
     * @return The found Image object.
     * @throws ImageNotFoundException If no image with the given ID exists.
     */
    public Image findById(Long id) {
        Optional<Image> image = imageRepository.findById(id);
        if (image.isEmpty()) {
            throw new ImageNotFoundException("Image not found");
        }
        return image.get();
    }

    /**
     * Creates a new image in the database.
     * @param image The Image object to create.
     */
    public void create(Image image) {
        imageRepository.save(image);
    }

    /**
     * Updates an existing image in the database.
     * @param image The Image object with updated information.
     */
    public void update(Image image) {
        Optional<Image> tmpImage = imageRepository.findById(image.getId());
        if (tmpImage.isPresent()) {
            Image existingImage = tmpImage.get();
            existingImage.setName(image.getName());
            existingImage.setPath(image.getPath());
            existingImage.setType(image.getType());
            imageRepository.save(existingImage);
        }
    }

    /**
     * Handles the upload of an image file.
     * @param file The MultipartFile containing the image file to upload.
     * @return ResponseEntity containing upload status and message.
     */
    @Transactional
    public ResponseEntity<ResponseDto> uploadImage(MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>(new ResponseDto("Image not sent", null), HttpStatus.BAD_REQUEST);
        }

        if (!imageValidator.isImageValid(file)) {
            return new ResponseEntity<>(new ResponseDto("Invalid image file format. Only image files are allowed.", null), HttpStatus.BAD_REQUEST);
        }

        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadDir + File.separator + fileName);
            file.transferTo(path.toFile());

            Image image = new Image();
            image.setName(file.getOriginalFilename());
            image.setPath(path.toString());
            image.setType(file.getContentType());
            image.setSize(file.getSize());
            create(image);

            return new ResponseEntity<>(new ResponseDto("File uploaded successfully", image.getId()), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(new ResponseDto("Failed to upload file", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves an image byte array by its ID.
     * @param id The ID of the image to retrieve.
     * @return ResponseEntity containing the image byte array and metadata.
     * @throws IOException If there is an error reading the image file.
     */
    public ResponseEntity<byte[]> getImageById(Long id) throws IOException {
        Image image = findById(id);
        Path path = Paths.get(image.getPath());
        byte[] imageBytes = Files.readAllBytes(path);
        String contentType = getContentType(image.getName()); // Function to determine content type
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + image.getName() + "\"")
                .body(imageBytes);
    }

    /**
     * Determines the content type based on the file name.
     * @param fileName The name of the file.
     * @return The content type string.
     */
    private String getContentType(String fileName) {
        // Simplified method; adjust to return actual content type based on file extension or content inspection
        return "application/octet-stream";
    }
}

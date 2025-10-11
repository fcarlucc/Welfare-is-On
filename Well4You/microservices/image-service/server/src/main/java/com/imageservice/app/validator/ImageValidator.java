package com.imageservice.app.validator;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.util.IOUtils;

/**
 * Validator component for validating image files based on magic numbers.
 */
@Component
public class ImageValidator {

    /**
     * Magic numbers of supported image file formats.
     */
    private static final byte[][] SUPPORTED_IMAGE_MAGIC_NUMBERS = {
            {(byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xE0}, // JPEG
            {(byte) 0x89, (byte) 0x50, (byte) 0x4E, (byte) 0x47}  // PNG
    };

    /**
     * Validates if the given file is a supported image format based on its magic number.
     * @param file The MultipartFile to validate.
     * @return true if the file is a valid image, false otherwise.
     */
    public boolean isImageValid(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return false;
        }

        try {
            byte[] fileBytes = IOUtils.toByteArray(file.getInputStream());
            for (byte[] magicNumber : SUPPORTED_IMAGE_MAGIC_NUMBERS) {
                if (startsWith(fileBytes, magicNumber)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Checks if the beginning of an array matches a specific prefix.
     * @param array The array to check.
     * @param prefix The prefix to match against.
     * @return true if the array starts with the given prefix, false otherwise.
     */
    private boolean startsWith(byte[] array, byte[] prefix) {
        if (array.length < prefix.length) {
            return false;
        }
        for (int i = 0; i < prefix.length; i++) {
            if (array[i] != prefix[i]) {
                return false;
            }
        }
        return true;
    }
}

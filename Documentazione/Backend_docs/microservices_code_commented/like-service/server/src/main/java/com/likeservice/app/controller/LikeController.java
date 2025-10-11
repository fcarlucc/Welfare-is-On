package com.likeservice.app.controller;

import com.likeservice.app.dto.LikeDto;
import com.likeservice.app.exception.LikeAlreadySetException;
import com.likeservice.app.exception.LikeNotFoundException;
import com.likeservice.app.mapper.LikeMapper;
import com.likeservice.app.model.Like;
import com.likeservice.app.service.LikeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controller class that handles HTTP requests related to likes for services.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/like")
public class LikeController {

    private final LikeService likeService;
    private final LikeMapper likeMapper;

    /**
     * Retrieves the number of likes for a specific service.
     *
     * @param serviceId The ID of the service to retrieve likes for.
     * @return ResponseEntity containing the number of likes as a Long.
     */
    @GetMapping("/service-likes")
    public ResponseEntity<Long> serviceLikes(@RequestParam Long serviceId) {
        Long likes = likeService.likesForService(serviceId);
        return new ResponseEntity<>(likes, HttpStatus.OK);
    }

    /**
     * Retrieves a map of service IDs to their respective like counts, ordered by most likes.
     *
     * @return ResponseEntity containing a map of service IDs to like counts as Long values.
     */
    @GetMapping("/most-likes-list")
    public ResponseEntity<Map<Long, Long>> mostLikesList() {
        Map<Long, Long> serviceLikeCounts = likeService.mostLikesforServices();
        return new ResponseEntity<>(serviceLikeCounts, HttpStatus.OK);
    }

    /**
     * Checks if a user has liked a specific service.
     *
     * @param likeDto DTO containing userId and serviceId to check.
     * @return ResponseEntity containing true if the user has liked the service, false otherwise.
     */
    @PostMapping("/is-service-liked")
    public ResponseEntity<Boolean> serviceLikes(@RequestBody @Valid LikeDto likeDto) {
        try {
            likeService.findByUserIdAndServiceId(likeDto.getUserId(), likeDto.getServiceId());
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (LikeNotFoundException ignored) {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }

    /**
     * Creates a new like for a service.
     *
     * @param likeDto DTO containing userId and serviceId for creating the like.
     * @return ResponseEntity containing the created Like object.
     * @throws LikeAlreadySetException If the user has already liked the service.
     */
    @PostMapping("/create")
    public ResponseEntity<Like> createLike(@RequestBody @Valid LikeDto likeDto) {
        try {
            likeService.findByUserIdAndServiceId(likeDto.getUserId(), likeDto.getServiceId());
            throw new LikeAlreadySetException("Like for the service already set");
        } catch (LikeNotFoundException ignored) {
        }

        Like like = likeMapper.likeDtoToLike(likeDto);
        likeService.create(like);
        return new ResponseEntity<>(like, HttpStatus.OK);
    }

    /**
     * Deletes a like for a service.
     *
     * @param likeDto DTO containing userId and serviceId for deleting the like.
     * @return ResponseEntity with HttpStatus.OK if the like was successfully deleted.
     */
    @PostMapping("/delete")
    public ResponseEntity<?> deleteLike(@RequestBody @Valid LikeDto likeDto) {
        Like like = likeService.findByUserIdAndServiceId(likeDto.getUserId(), likeDto.getServiceId());
        likeService.delete(like);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

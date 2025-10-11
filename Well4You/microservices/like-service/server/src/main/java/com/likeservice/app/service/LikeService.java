package com.likeservice.app.service;

import com.likeservice.app.model.Like;
import com.likeservice.app.exception.LikeNotFoundException;
import com.likeservice.app.repository.ILikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing operations related to likes.
 */
@Service
@RequiredArgsConstructor
public class LikeService {

    private final ILikeRepository likeRepository;

    /**
     * Retrieves the number of likes for a given service.
     *
     * @param serviceId The ID of the service to retrieve likes for.
     * @return The number of likes for the service.
     */
    public Long likesForService(Long serviceId) {
        List<Like> likesList = likeRepository.findAllByServiceId(serviceId);
        return (long) likesList.size();
    }

    /**
     * Retrieves a like based on the given userId and serviceId.
     *
     * @param userId    The ID of the user who liked the service.
     * @param serviceId The ID of the service that was liked.
     * @return The like if found.
     * @throws LikeNotFoundException If no like is found for the given userId and serviceId.
     */
    public Like findByUserIdAndServiceId(Long userId, Long serviceId) {
        Optional<Like> like = likeRepository.findByUserIdAndServiceId(userId, serviceId);
        return like.orElseThrow(() -> new LikeNotFoundException("Like does not exist"));
    }

    /**
     * Retrieves a like based on the given userId.
     *
     * @param userId The ID of the user to retrieve the like for.
     * @return The like if found.
     * @throws LikeNotFoundException If no like is found for the given userId.
     */
    public Like findByUserId(Long userId) {
        Optional<Like> like = likeRepository.findByUserId(userId);
        return like.orElseThrow(() -> new LikeNotFoundException("Like does not exist"));
    }

    /**
     * Retrieves a like based on the given like ID.
     *
     * @param id The ID of the like to retrieve.
     * @return The like if found.
     * @throws LikeNotFoundException If no like is found for the given ID.
     */
    public Like findById(Long id) {
        Optional<Like> like = likeRepository.findById(id);
        return like.orElseThrow(() -> new LikeNotFoundException("Like id does not exist"));
    }

    /**
     * Saves a new like or updates an existing one.
     *
     * @param like The like to be saved or updated.
     */
    public void create(Like like) {
        likeRepository.save(like);
    }

    /**
     * Deletes a like.
     *
     * @param like The like to be deleted.
     */
    public void delete(Like like) {
        likeRepository.delete(like);
    }

    /**
     * Updates an existing like.
     *
     * @param like The like to be updated.
     */
    public void update(Like like) {
        likeRepository.save(like);
    }

    /**
     * Retrieves the count of likes for each service.
     *
     * @return A map where keys are service IDs and values are counts of likes for each service.
     */
    public Map<Long, Long> mostLikesforServices() {
        return likeRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(Like::getServiceId, Collectors.counting()));
    }
}

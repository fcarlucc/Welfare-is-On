package com.likeservice.app.repository;

import com.likeservice.app.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for accessing 'like' data in the database.
 */
public interface ILikeRepository extends JpaRepository<Like, Long> {

    /**
     * Retrieves a like based on the given userId and serviceId.
     *
     * @param userId    The ID of the user who liked the service.
     * @param serviceId The ID of the service that was liked.
     * @return An Optional containing the like if found, otherwise empty.
     */
    Optional<Like> findByUserIdAndServiceId(Long userId, Long serviceId);

    /**
     * Retrieves a like based on the given userId.
     *
     * @param userId The ID of the user to retrieve likes for.
     * @return An Optional containing the like if found, otherwise empty.
     */
    Optional<Like> findByUserId(Long userId);

    /**
     * Retrieves all likes for a given serviceId.
     *
     * @param serviceId The ID of the service to retrieve likes for.
     * @return A list of likes associated with the service.
     */
    List<Like> findAllByServiceId(Long serviceId);
}

package com.shopservice.app.repository;

import com.shopservice.app.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing purchases in the database.
 */
public interface IPurchaseRepository extends JpaRepository<Purchase, Long> {

    /**
     * Retrieves all purchases made for a specific service.
     *
     * @param serviceId The ID of the service to retrieve purchases for.
     * @return A list of purchases made for the specified service.
     */
    List<Purchase> findAllByServiceId(Long serviceId);

    /**
     * Retrieves a specific purchase made by a user for a service.
     *
     * @param userId    The ID of the user who made the purchase.
     * @param serviceId The ID of the service for which the purchase was made.
     * @return An Optional containing the purchase, if found; otherwise an empty Optional.
     */
    Optional<Purchase> findByUserIdAndServiceId(Long userId, Long serviceId);
}

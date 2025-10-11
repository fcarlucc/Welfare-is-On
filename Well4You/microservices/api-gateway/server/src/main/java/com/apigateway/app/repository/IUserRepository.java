package com.apigateway.app.repository;

import com.apigateway.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing User entities.
 */
@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a User by their email address.
     * @param email String representing the email address of the user to find.
     * @return Optional User containing the found User, or empty if not found.
     */
    Optional<User> findByEmail(String email);
}

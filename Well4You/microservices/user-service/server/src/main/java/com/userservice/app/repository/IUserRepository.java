package com.userservice.app.repository;

import com.userservice.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing {@link User} entities.
 * <p>
 * This interface extends {@link JpaRepository} to provide standard CRUD operations
 * for {@link User} entities. It also includes a custom method to find users by their
 * email address.
 * </p>
 *
 * <p>Custom Methods:</p>
 * <ul>
 *     <li>{@link #findByEmail(String)}: Retrieves a {@link User} entity by its email address.</li>
 * </ul>
 */
@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a {@link User} entity by its email address.
     *
     * @param email the email address of the user to find
     * @return an {@link Optional} containing the {@link User} if found, or an empty {@link Optional} if no user exists with the given email
     */
    Optional<User> findByEmail(String email);
}

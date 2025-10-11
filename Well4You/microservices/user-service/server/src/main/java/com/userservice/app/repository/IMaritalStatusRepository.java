package com.userservice.app.repository;

import com.userservice.app.model.MaritalStatus;
import com.userservice.app.model.enumerator.MaritalStatusName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing {@link MaritalStatus} entities.
 * <p>
 * This interface extends {@link JpaRepository} to provide basic CRUD operations
 * for {@link MaritalStatus} entities. Additionally, it includes a custom method
 * to find a marital status by its name.
 * </p>
 *
 * <p>Custom Methods:</p>
 * <ul>
 *     <li>{@link #findByName(MaritalStatusName)}: Retrieves a {@link MaritalStatus} entity
 *     based on the specified {@link MaritalStatusName}.</li>
 * </ul>
 */
public interface IMaritalStatusRepository extends JpaRepository<MaritalStatus, Integer> {

    /**
     * Finds a {@link MaritalStatus} entity by its name.
     *
     * @param name the {@link MaritalStatusName} representing the name of the marital status to find
     * @return an {@link Optional} containing the {@link MaritalStatus} if found, or an empty {@link Optional} if not
     */
    Optional<MaritalStatus> findByName(MaritalStatusName name);
}

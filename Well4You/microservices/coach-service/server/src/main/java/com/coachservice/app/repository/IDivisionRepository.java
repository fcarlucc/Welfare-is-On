package com.coachservice.app.repository;

import com.coachservice.app.model.Division;
import com.coachservice.app.model.enumerator.DivisionName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing {@link Division} entities.
 * <p>
 * This interface extends {@link JpaRepository}, providing standard CRUD operations and a custom query method
 * for accessing {@link Division} entities in the database.
 * </p>
 *
 * <p>Key method:</p>
 * <ul>
 *     <li>{@link #findByName(DivisionName)}: Retrieves a {@link Division} entity based on its name.</li>
 * </ul>
 *
 * <p>The method uses Spring Data JPA's query derivation feature to automatically implement the query logic for
 * finding a division by its {@link DivisionName} enumeration.</p>
 *
 * @see Division
 * @see DivisionName
 */
public interface IDivisionRepository extends JpaRepository<Division, Long> {

    /**
     * Finds a {@link Division} entity by its {@link DivisionName}.
     *
     * @param name the name of the division to search for
     * @return an {@link Optional} containing the {@link Division} if found, or {@link Optional#empty()} if not
     */
    Optional<Division> findByName(DivisionName name);
}

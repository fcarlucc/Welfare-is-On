package com.coachservice.app.repository;

import com.coachservice.app.model.Pillar;
import com.coachservice.app.model.enumerator.PillarName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing {@link Pillar} entities.
 * <p>
 * Extends {@link JpaRepository} to provide basic CRUD operations and includes custom query methods
 * for accessing {@link Pillar} entities in the database.
 * </p>
 *
 * <p>Key method:</p>
 * <ul>
 *     <li>{@link #findByName(PillarName)}: Retrieves a {@link Pillar} entity by its {@link PillarName} enumeration.</li>
 * </ul>
 *
 * <p>This repository uses Spring Data JPA to automatically implement the query logic based on the
 * provided enumeration value.</p>
 *
 * @see Pillar
 * @see PillarName
 */
public interface IPillarRepository extends JpaRepository<Pillar, Integer> {

    /**
     * Finds a {@link Pillar} entity by its {@link PillarName}.
     *
     * @param name the name of the pillar to search for
     * @return an {@link Optional} containing the {@link Pillar} if found, or {@link Optional#empty()} if not
     */
    Optional<Pillar> findByName(PillarName name);
}

package com.coachservice.app.repository;

import com.coachservice.app.model.Coach;
import com.coachservice.app.model.Pillar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing {@link Coach} entities.
 * <p>
 * This interface extends {@link JpaRepository}, providing CRUD operations and custom query methods
 * for accessing and manipulating Coach entities in the database.
 * </p>
 *
 * <p>Key methods include:</p>
 * <ul>
 *     <li>{@link #findAllBySpecialization(Pillar)}: Retrieves a list of coaches who specialize in a given {@link Pillar}.</li>
 * </ul>
 *
 * <p>This repository leverages Spring Data JPA's query derivation feature to automatically implement
 * the method for querying coaches based on their specialization.</p>
 *
 * @see Coach
 * @see Pillar
 */
public interface ICoachRepository extends JpaRepository<Coach, Long> {

    /**
     * Finds all coaches whose specialization matches the given {@link Pillar}.
     *
     * @param pillar the specialization to search for
     * @return a list of {@link Coach} entities with the specified specialization
     */
    List<Coach> findAllBySpecialization(Pillar pillar);
}

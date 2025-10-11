package com.servicesservice.app.repository;

import com.servicesservice.app.model.Pillar;
import com.servicesservice.app.model.enumerator.PillarName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for accessing Pillar entities in the database.
 */
public interface IPillarRepository extends JpaRepository<Pillar, Integer> {

    /**
     * Retrieves a Pillar entity by its name.
     *
     * @param name The name of the Pillar to retrieve
     * @return An Optional containing the Pillar entity if found, otherwise empty
     */
    Optional<Pillar> findByName(PillarName name);
}

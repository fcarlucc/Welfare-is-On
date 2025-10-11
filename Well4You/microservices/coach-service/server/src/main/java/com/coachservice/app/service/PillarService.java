package com.coachservice.app.service;

import com.coachservice.app.exception.PillarNotFoundException;
import com.coachservice.app.model.Pillar;
import com.coachservice.app.model.enumerator.PillarName;
import com.coachservice.app.repository.IPillarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing {@link Pillar} entities.
 * <p>
 * Provides operations to find, save, and retrieve {@link Pillar} entities.
 * Handles exceptions related to missing pillars and interacts with the {@link IPillarRepository}
 * for persistence operations.
 * </p>
 *
 * <p>Key methods:</p>
 * <ul>
 *     <li>{@link #findByName(PillarName)}: Retrieves a {@link Pillar} by its name.
 *     Throws {@link PillarNotFoundException} if no pillar with the specified name is found.</li>
 *     <li>{@link #save(Pillar)}: Saves a {@link Pillar} entity to the repository.</li>
 *     <li>{@link #findAll()}: Retrieves a list of all {@link Pillar} entities from the repository.</li>
 * </ul>
 *
 * <p>Dependencies:</p>
 * <ul>
 *     <li>{@link IPillarRepository}: Repository interface for performing CRUD operations on {@link Pillar} entities.</li>
 * </ul>
 */
@Service
@RequiredArgsConstructor
public class PillarService {

    private final IPillarRepository pillarRepository;

    /**
     * Retrieves a {@link Pillar} entity by its name.
     *
     * @param pillarName the name of the pillar to retrieve
     * @return the {@link Pillar} entity if found
     * @throws PillarNotFoundException if no pillar with the specified name is found
     */
    public Pillar findByName(PillarName pillarName) {
        Optional<Pillar> pillar = pillarRepository.findByName(pillarName);
        if (pillar.isEmpty()) {
            throw new PillarNotFoundException("pillar not found");
        }
        return pillar.get();
    }

    /**
     * Saves a {@link Pillar} entity to the repository.
     *
     * @param pillar the {@link Pillar} entity to save
     * @return the saved {@link Pillar} entity
     */
    public Pillar save(Pillar pillar) {
        return pillarRepository.save(pillar);
    }

    /**
     * Retrieves a list of all {@link Pillar} entities from the repository.
     *
     * @return a list of {@link Pillar} entities
     */
    public List<Pillar> findAll() {
        return pillarRepository.findAll();
    }
}

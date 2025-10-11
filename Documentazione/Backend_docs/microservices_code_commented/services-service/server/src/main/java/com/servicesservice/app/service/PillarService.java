package com.servicesservice.app.service;

import com.servicesservice.app.model.Pillar;
import com.servicesservice.app.exception.PillarNotFoundException;
import com.servicesservice.app.model.enumerator.PillarName;
import com.servicesservice.app.repository.IPillarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing Pillar entities.
 */
@Service
@RequiredArgsConstructor
public class PillarService {

    private final IPillarRepository pillarRepository;

    /**
     * Retrieves a Pillar entity by its name.
     *
     * @param pillarName The name of the Pillar to retrieve
     * @return The found Pillar entity
     * @throws PillarNotFoundException if no Pillar with the given name is found
     */
    public Pillar findByName(PillarName pillarName) {
        Optional<Pillar> pillar = pillarRepository.findByName(pillarName);
        if (pillar.isEmpty()) {
            throw new PillarNotFoundException("Pillar not found");
        }
        return pillar.get();
    }

    /**
     * Saves a Pillar entity.
     *
     * @param pillar The Pillar entity to save
     * @return The saved Pillar entity
     */
    public Pillar save(Pillar pillar) {
        return pillarRepository.save(pillar);
    }

    /**
     * Retrieves all Pillar entities.
     *
     * @return List of all Pillar entities
     */
    public List<Pillar> findAll() {
        return pillarRepository.findAll();
    }
}

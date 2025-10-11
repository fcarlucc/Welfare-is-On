package com.userservice.app.service;

import com.userservice.app.model.Interest;
import com.userservice.app.model.enumerator.InterestName;
import com.userservice.app.repository.IInterestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing {@link Interest} entities.
 * <p>
 * This service provides methods for interacting with the {@link Interest} repository,
 * allowing for operations such as finding, saving, and retrieving all interests.
 * </p>
 *
 * <p>Key functionalities:</p>
 * <ul>
 *     <li>{@link #findByName(InterestName)}: Retrieves an {@code Interest} entity by its name, encapsulated within an {@link Optional}.</li>
 *     <li>{@link #save(Interest)}: Persists a given {@code Interest} entity to the database and returns the saved instance.</li>
 *     <li>{@link #findAll()}: Retrieves a list of all {@code Interest} entities present in the database.</li>
 * </ul>
 *
 * <p>Usage:</p>
 * <ul>
 *     <li>Use {@link #findByName(InterestName)} when you need to find an interest based on its name.</li>
 *     <li>Use {@link #save(Interest)} to save or update an interest entity.</li>
 *     <li>Use {@link #findAll()} to retrieve all available interests.</li>
 * </ul>
 *
 * <p>Dependencies:</p>
 * <ul>
 *     <li>{@link IInterestRepository}: The repository interface used for data access operations on {@code Interest} entities.</li>
 * </ul>
 */
@Service
@RequiredArgsConstructor
public class InterestService {

    private final IInterestRepository interestRepository;

    /**
     * Finds an {@code Interest} entity by its name.
     *
     * @param interestName the name of the interest to find
     * @return an {@link Optional} containing the found interest, or {@code empty} if no interest with the given name exists
     */
    public Optional<Interest> findByName(InterestName interestName) {
        return interestRepository.findByName(interestName);
    }

    /**
     * Saves a given {@code Interest} entity to the database.
     *
     * @param interest the interest entity to be saved
     * @return the saved {@code Interest} entity
     */
    public Interest save(Interest interest) {
        return interestRepository.save(interest);
    }

    /**
     * Retrieves all {@code Interest} entities from the database.
     *
     * @return a list of all {@code Interest} entities
     */
    public List<Interest> findAll() {
        return interestRepository.findAll();
    }
}

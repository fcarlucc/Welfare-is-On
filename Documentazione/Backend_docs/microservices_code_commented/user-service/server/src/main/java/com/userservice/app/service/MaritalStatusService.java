package com.userservice.app.service;

import com.userservice.app.model.MaritalStatus;
import com.userservice.app.model.enumerator.MaritalStatusName;
import com.userservice.app.repository.IMaritalStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing {@link MaritalStatus} entities.
 * <p>
 * This service provides methods to perform operations related to {@link MaritalStatus},
 * such as retrieving, saving, and listing marital status records.
 * </p>
 *
 * <p>Key functionalities:</p>
 * <ul>
 *     <li>{@link #findByName(MaritalStatusName)}: Retrieves an {@code MaritalStatus} entity based on its name, encapsulated within an {@link Optional}.</li>
 *     <li>{@link #save(MaritalStatus)}: Persists a given {@code MaritalStatus} entity to the database and returns the saved instance.</li>
 *     <li>{@link #findAll()}: Retrieves a list of all {@code MaritalStatus} entities present in the database.</li>
 * </ul>
 *
 * <p>Usage:</p>
 * <ul>
 *     <li>Use {@link #findByName(MaritalStatusName)} to find a specific marital status by its name.</li>
 *     <li>Use {@link #save(MaritalStatus)} to save or update a marital status entity.</li>
 *     <li>Use {@link #findAll()} to retrieve all available marital status records.</li>
 * </ul>
 *
 * <p>Dependencies:</p>
 * <ul>
 *     <li>{@link IMaritalStatusRepository}: The repository interface used for data access operations on {@code MaritalStatus} entities.</li>
 * </ul>
 */
@Service
@RequiredArgsConstructor
public class MaritalStatusService {

    private final IMaritalStatusRepository maritalStatusRepository;

    /**
     * Finds a {@code MaritalStatus} entity by its name.
     *
     * @param maritalStatusName the name of the marital status to find
     * @return an {@link Optional} containing the found marital status, or {@code empty} if no marital status with the given name exists
     */
    public Optional<MaritalStatus> findByName(MaritalStatusName maritalStatusName) {
        return maritalStatusRepository.findByName(maritalStatusName);
    }

    /**
     * Saves a given {@code MaritalStatus} entity to the database.
     *
     * @param maritalStatus the marital status entity to be saved
     * @return the saved {@code MaritalStatus} entity
     */
    public MaritalStatus save(MaritalStatus maritalStatus) {
        return maritalStatusRepository.save(maritalStatus);
    }

    /**
     * Retrieves all {@code MaritalStatus} entities from the database.
     *
     * @return a list of all {@code MaritalStatus} entities
     */
    public List<MaritalStatus> findAll() {
        return maritalStatusRepository.findAll();
    }
}

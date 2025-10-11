package com.coachservice.app.service;

import com.coachservice.app.exception.DivisionNotFoundException;
import com.coachservice.app.model.Division;
import com.coachservice.app.model.enumerator.DivisionName;
import com.coachservice.app.repository.IDivisionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing {@link Division} entities.
 * <p>
 * Provides operations to find, save, and retrieve {@link Division} entities.
 * Handles exceptions related to missing divisions and interacts with the {@link IDivisionRepository}
 * for persistence operations.
 * </p>
 *
 * <p>Key methods:</p>
 * <ul>
 *     <li>{@link #findByName(DivisionName)}: Retrieves a {@link Division} by its name.
 *     Throws {@link DivisionNotFoundException} if no division with the specified name is found.</li>
 *     <li>{@link #save(Division)}: Saves a {@link Division} entity to the repository.</li>
 *     <li>{@link #findAll()}: Retrieves a list of all {@link Division} entities from the repository.</li>
 * </ul>
 *
 * <p>Dependencies:</p>
 * <ul>
 *     <li>{@link IDivisionRepository}: Repository interface for performing CRUD operations on {@link Division} entities.</li>
 * </ul>
 */
@Service
@RequiredArgsConstructor
public class DivisionService {

    private final IDivisionRepository divisionRepository;

    /**
     * Retrieves a {@link Division} entity by its name.
     *
     * @param divisionName the name of the division to retrieve
     * @return the {@link Division} entity if found
     * @throws DivisionNotFoundException if no division with the specified name is found
     */
    public Division findByName(DivisionName divisionName) {
        Optional<Division> division = divisionRepository.findByName(divisionName);
        if (division.isEmpty()) {
            throw new DivisionNotFoundException("division not found");
        }
        return division.get();
    }

    /**
     * Saves a {@link Division} entity to the repository.
     *
     * @param division the {@link Division} entity to save
     * @return the saved {@link Division} entity
     */
    public Division save(Division division) {
        return divisionRepository.save(division);
    }

    /**
     * Retrieves a list of all {@link Division} entities from the repository.
     *
     * @return a list of {@link Division} entities
     */
    public List<Division> findAll() {
        return divisionRepository.findAll();
    }
}

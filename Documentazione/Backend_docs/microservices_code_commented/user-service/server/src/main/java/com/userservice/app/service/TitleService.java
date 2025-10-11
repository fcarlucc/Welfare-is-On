package com.userservice.app.service;

import com.userservice.app.model.Title;
import com.userservice.app.model.enumerator.TitleName;
import com.userservice.app.repository.ITitleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing {@link Title} entities.
 * <p>
 * This service provides methods to handle {@link Title} entities, including retrieval by name,
 * saving new titles, and listing all titles.
 * </p>
 *
 * <p>Key functionalities:</p>
 * <ul>
 *     <li>{@link #findByName(TitleName)}: Retrieves an {@code Title} entity based on its name, encapsulated in an {@link Optional}.</li>
 *     <li>{@link #save(Title)}: Saves a {@code Title} entity to the database and returns the saved instance.</li>
 *     <li>{@link #findAll()}: Retrieves a list of all {@code Title} entities present in the database.</li>
 * </ul>
 *
 * <p>Usage:</p>
 * <ul>
 *     <li>Use {@link #findByName(TitleName)} to locate a specific title by its name.</li>
 *     <li>Use {@link #save(Title)} to save or update a title entity.</li>
 *     <li>Use {@link #findAll()} to fetch all available title records.</li>
 * </ul>
 *
 * <p>Dependencies:</p>
 * <ul>
 *     <li>{@link ITitleRepository}: The repository interface used for data access operations on {@code Title} entities.</li>
 * </ul>
 */
@Service
@RequiredArgsConstructor
public class TitleService {

    private final ITitleRepository titleRepository;

    /**
     * Finds a {@code Title} entity by its name.
     *
     * @param titleName the name of the title to find
     * @return an {@link Optional} containing the found title, or {@code empty} if no title with the given name exists
     */
    public Optional<Title> findByName(TitleName titleName) {
        return titleRepository.findByName(titleName);
    }

    /**
     * Saves a given {@code Title} entity to the database.
     *
     * @param title the title entity to be saved
     * @return the saved {@code Title} entity
     */
    public Title save(Title title) {
        return titleRepository.save(title);
    }

    /**
     * Retrieves all {@code Title} entities from the database.
     *
     * @return a list of all {@code Title} entities
     */
    public List<Title> findAll() {
        return titleRepository.findAll();
    }
}

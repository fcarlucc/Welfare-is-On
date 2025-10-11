package com.userservice.app.repository;

import com.userservice.app.model.Title;
import com.userservice.app.model.enumerator.TitleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing {@link Title} entities.
 * <p>
 * This interface extends {@link JpaRepository} to offer standard CRUD operations
 * for {@link Title} entities. Additionally, it provides a custom method to
 * retrieve a title based on its name.
 * </p>
 *
 * <p>Custom Methods:</p>
 * <ul>
 *     <li>{@link #findByName(TitleName)}: Finds a {@link Title} entity by its
 *     {@link TitleName}.</li>
 * </ul>
 */
public interface ITitleRepository extends JpaRepository<Title, Long> {

    /**
     * Retrieves a {@link Title} entity based on its {@link TitleName}.
     *
     * @param name the {@link TitleName} representing the name of the title to find
     * @return an {@link Optional} containing the {@link Title} if it exists, or an empty {@link Optional} if not
     */
    Optional<Title> findByName(TitleName name);
}

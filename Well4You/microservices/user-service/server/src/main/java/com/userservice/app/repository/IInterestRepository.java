package com.userservice.app.repository;

import com.userservice.app.model.Interest;
import com.userservice.app.model.enumerator.InterestName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for accessing {@link Interest} entities.
 * <p>
 * This interface extends {@link JpaRepository} and provides methods to perform CRUD operations
 * on {@link Interest} entities. It includes custom query methods to retrieve interests by their name.
 * </p>
 *
 * <p>Custom Methods:</p>
 * <ul>
 *     <li>{@link #findByName(InterestName)}: Retrieves an {@link Interest} entity based on its {@link InterestName}.</li>
 * </ul>
 */
public interface IInterestRepository extends JpaRepository<Interest, Long> {

    /**
     * Finds an {@link Interest} entity by its name.
     *
     * @param name the {@link InterestName} of the interest to retrieve
     * @return an {@link Optional} containing the {@link Interest} if found, or an empty {@link Optional} if not
     */
    Optional<Interest> findByName(InterestName name);
}

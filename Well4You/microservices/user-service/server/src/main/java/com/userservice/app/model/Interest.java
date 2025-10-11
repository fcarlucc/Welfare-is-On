package com.userservice.app.model;

import com.userservice.app.model.enumerator.InterestName;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an interest entity within the system.
 * <p>
 * This class maps to the "interest" table in the database and defines the {@code Interest} entity.
 * Each interest is identified by a unique ID and represented by an enumerated value of {@link InterestName}.
 * </p>
 *
 * <p>The Interest entity includes:</p>
 * <ul>
 *     <li>{@code id}: The unique identifier for the interest (primary key), automatically generated.</li>
 *     <li>{@code name}: The name of the interest, stored as an enumerated value of {@link InterestName}.</li>
 * </ul>
 *
 * <p>Key features:</p>
 * <ul>
 *     <li>Uses Jakarta Persistence annotations for entity mapping and auto-generated ID.</li>
 *     <li>Employs Lombok annotations to reduce boilerplate code for getters, setters, and constructors.</li>
 *     <li>Represents interests using the {@link InterestName} enumeration, ensuring a controlled set of interest values.</li>
 * </ul>
 *
 * <p>Constructor:</p>
 * <ul>
 *     <li>Single-argument constructor for initializing with a specific interest name.</li>
 * </ul>
 */
@Entity
@Data
@NoArgsConstructor
public class Interest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InterestName name;

    /**
     * Constructs an {@code Interest} with the specified interest name.
     *
     * @param name the name of the interest
     */
    public Interest(InterestName name) {
        this.name = name;
    }
}

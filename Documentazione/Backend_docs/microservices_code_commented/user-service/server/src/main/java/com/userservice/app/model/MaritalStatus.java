package com.userservice.app.model;

import com.userservice.app.model.enumerator.MaritalStatusName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a marital status entity within the system.
 * <p>
 * This class maps to the "marital_status" table in the database and defines the {@code MaritalStatus} entity.
 * Each marital status is identified by a unique ID and represented by an enumerated value of {@link MaritalStatusName}.
 * </p>
 *
 * <p>The MaritalStatus entity includes:</p>
 * <ul>
 *     <li>{@code id}: The unique identifier for the marital status (primary key), automatically generated.</li>
 *     <li>{@code name}: The name of the marital status, stored as an enumerated value of {@link MaritalStatusName}.</li>
 * </ul>
 *
 * <p>Key features:</p>
 * <ul>
 *     <li>Uses Jakarta Persistence annotations for entity mapping and auto-generated ID.</li>
 *     <li>Employs Lombok annotations to streamline code by automatically generating getters, setters, and constructors.</li>
 *     <li>Represents the marital status using the {@link MaritalStatusName} enumeration, ensuring a controlled set of status values.</li>
 * </ul>
 *
 * <p>Constructors:</p>
 * <ul>
 *     <li>No-args constructor for default initialization.</li>
 *     <li>All-args constructor for initializing with a specific marital status name.</li>
 * </ul>
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaritalStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MaritalStatusName name;

    /**
     * Constructs a {@code MaritalStatus} with the specified marital status name.
     *
     * @param name the name of the marital status
     */
    public MaritalStatus(MaritalStatusName name) {
        this.name = name;
    }
}

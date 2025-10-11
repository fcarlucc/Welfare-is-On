package com.userservice.app.model;

import com.userservice.app.model.enumerator.TitleName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a title entity in the system.
 * <p>
 * This class defines the {@code Title} entity, which represents a title that can be associated with users.
 * It maps to the "title" table in the database. The title is stored as an enumerated type {@link TitleName}.
 * </p>
 *
 * <p>The Title entity includes:</p>
 * <ul>
 *     <li>{@code id}: A unique identifier for the title (primary key), auto-generated.</li>
 *     <li>{@code name}: The name of the title, represented as an enumerated value of {@link TitleName}.</li>
 * </ul>
 *
 * <p>Key aspects:</p>
 * <ul>
 *     <li>Uses Jakarta Persistence annotations for entity mapping and primary key generation.</li>
 *     <li>Employs Lombok annotations to automatically generate boilerplate code such as getters, setters, and constructors.</li>
 *     <li>Defines the title name using the {@link TitleName} enumeration to ensure a fixed set of valid title values.</li>
 * </ul>
 *
 * <p>Constructors:</p>
 * <ul>
 *     <li>No-args constructor for default initialization.</li>
 *     <li>All-args constructor for full initialization of the title with its name.</li>
 * </ul>
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Title {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TitleName name;

    /**
     * Constructs a {@code Title} with the specified title name.
     *
     * @param name the name of the title
     */
    public Title(TitleName name) {
        this.name = name;
    }
}

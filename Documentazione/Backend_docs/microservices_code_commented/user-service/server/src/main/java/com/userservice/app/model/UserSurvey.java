package com.userservice.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a user's survey information in the system.
 * <p>
 * This class defines the {@code UserSurvey} entity, which stores additional details provided by a user
 * regarding their personal circumstances and preferences. It maps to the "user_survey" table in the
 * "userservice" schema of the database.
 * </p>
 *
 * <p>The UserSurvey entity includes:</p>
 * <ul>
 *     <li>{@code id}: A unique identifier for the survey (primary key), auto-generated.</li>
 *     <li>{@code user}: A one-to-one relationship with the {@link User} entity, representing the user associated with this survey.</li>
 *     <li>{@code hasChildren}: Indicates whether the user has children.</li>
 *     <li>{@code hasElderlyParents}: Indicates whether the user has elderly parents.</li>
 *     <li>{@code maritalStatus}: A many-to-one relationship with the {@link MaritalStatus} entity, representing the user's marital status.</li>
 *     <li>{@code title}: A many-to-one relationship with the {@link Title} entity, representing the user's title.</li>
 *     <li>{@code interests}: A many-to-many relationship with the {@link Interest} entity, representing the user's interests.</li>
 * </ul>
 *
 * <p>Key aspects:</p>
 * <ul>
 *     <li>Uses Jakarta Persistence annotations for entity mapping and relationship management.</li>
 *     <li>Employs Lombok annotations to automatically generate boilerplate code such as getters, setters, and constructors.</li>
 *     <li>Defines relationships with {@link User}, {@link MaritalStatus}, {@link Title}, and {@link Interest} entities.</li>
 * </ul>
 *
 * <p>Constructors:</p>
 * <ul>
 *     <li>No-args constructor for default initialization.</li>
 *     <li>All-args constructor for full initialization of all fields, except the user.</li>
 * </ul>
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_survey", schema = "userservice")
public class UserSurvey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_survey_id", nullable = false, updatable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "has_children")
    private boolean hasChildren;

    @Column(name = "has_elderly_parents")
    private boolean hasElderlyParents;

    @ManyToOne
    @JoinColumn(name = "marital_status_id")
    private MaritalStatus maritalStatus;

    @ManyToOne
    @JoinColumn(name = "title_id")
    private Title title;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_interest",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "interest_id")
    )
    private Set<Interest> interests = new HashSet<>();

    /**
     * Constructs a {@code UserSurvey} with the specified details, excluding the associated user.
     *
     * @param maritalStatus the marital status of the user
     * @param title the title of the user
     * @param hasChildren flag indicating if the user has children
     * @param hasElderlyParents flag indicating if the user has elderly parents
     * @param interests the set of interests associated with the user
     */
    public UserSurvey(MaritalStatus maritalStatus, Title title, boolean hasChildren, boolean hasElderlyParents, Set<Interest> interests) {
        this.maritalStatus = maritalStatus;
        this.title = title;
        this.hasChildren = hasChildren;
        this.hasElderlyParents = hasElderlyParents;
        this.interests = interests;
    }
}

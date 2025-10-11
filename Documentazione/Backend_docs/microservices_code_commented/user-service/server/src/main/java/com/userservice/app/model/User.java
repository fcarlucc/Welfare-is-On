package com.userservice.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Represents a user in the system.
 * <p>
 * This class defines the User entity with attributes such as personal information, geographical location,
 * and associated user survey details. It maps to the "user" table in the "userservice" schema of the database.
 * </p>
 *
 * <p>The User entity includes:</p>
 * <ul>
 *     <li>{@code id}: A unique identifier for the user (primary key).</li>
 *     <li>{@code firstName}: The user's first name.</li>
 *     <li>{@code lastName}: The user's last name.</li>
 *     <li>{@code dob}: The user's date of birth.</li>
 *     <li>{@code email}: The user's email address, which must be unique.</li>
 *     <li>{@code longitude}: The user's geographical longitude.</li>
 *     <li>{@code latitude}: The user's geographical latitude.</li>
 *     <li>{@code savedMoney}: The amount of money saved by the user.</li>
 *     <li>{@code userSurvey}: A one-to-one relationship with the {@link UserSurvey} entity, representing additional survey details.</li>
 * </ul>
 *
 * <p>Key aspects:</p>
 * <ul>
 *     <li>Uses Jakarta Persistence annotations to map the class to a database table.</li>
 *     <li>Employs Lombok annotations to automatically generate boilerplate code such as getters, setters, and constructors.</li>
 *     <li>Defines cascading and fetch strategies for the relationship with {@link UserSurvey}.</li>
 * </ul>
 *
 * <p>Constructors:</p>
 * <ul>
 *     <li>No-args constructor for default initialization.</li>
 *     <li>All-args constructor for full initialization of all fields.</li>
 * </ul>
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user", schema = "userservice")
public class User {

    @Id
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(updatable = false)
    private Date dob;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "saved_money")
    private double savedMoney;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private UserSurvey userSurvey;

    /**
     * Constructs a {@code User} with the specified details, excluding the {@code userSurvey}.
     *
     * @param id the unique identifier for the user
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param email the user's email address
     * @param longitude the user's geographical longitude
     * @param latitude the user's geographical latitude
     * @param dob the user's date of birth
     */
    public User(Long id, String firstName, String lastName, String email, double longitude, double latitude, Date dob) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.longitude = longitude;
        this.latitude = latitude;
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstname='" + firstName + '\'' +
                ", lastname='" + lastName + '\'' +
                ", dob=" + dob +
                ", email='" + email + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", userSurvey=" + userSurvey +
                '}';
    }
}

package com.coachservice.app.model;

import com.coachservice.app.model.enumerator.PillarName;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Represents a Coach entity within the coach service application.
 * <p>
 * This entity stores details about a coach, including personal information, contact details, specialization,
 * and division. It maps to the "coach" table in the "coachservice" schema.
 * </p>
 *
 * <p>Key attributes include:</p>
 * <ul>
 *     <li><b>id:</b> Unique identifier for the coach. It is not updatable.</li>
 *     <li><b>firstName:</b> The coach's first name. It is a required field.</li>
 *     <li><b>lastName:</b> The coach's last name. It is a required field.</li>
 *     <li><b>email:</b> The coach's email address, which must be unique and properly formatted.</li>
 *     <li><b>imageId:</b> Identifier for the coach's image, stored as a Long.</li>
 *     <li><b>phoneNumber:</b> The coach's phone number, which must be unique.</li>
 *     <li><b>longitude:</b> The geographical longitude of the coach's location. It cannot be null.</li>
 *     <li><b>latitude:</b> The geographical latitude of the coach's location. It cannot be null.</li>
 *     <li><b>division:</b> The division to which the coach belongs. This is a many-to-one relationship with the Division entity.</li>
 *     <li><b>specialization:</b> The coach's area of specialization, represented by a Pillar entity.</li>
 * </ul>
 *
 * <p>The class also includes a custom {@link #toString()} method to provide a string representation of the coach object
 * for debugging and logging purposes.</p>
 *
 * @see Division
 * @see Pillar
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "coach", schema = "coachservice")
public class Coach {

    @Id
    @Column(name = "coach_id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "image_id", nullable = false)
    private Long imageId;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @NotNull(message = "longitude cannot be null")
    private Double longitude;

    @NotNull(message = "latitude cannot be null")
    private Double latitude;

    @ManyToOne
    @JoinColumn(name = "division_id")
    private Division division;

    @ManyToOne
    @JoinColumn(name = "specialization_id")
    private Pillar specialization;

    /**
     * Constructs a new Coach with the specified attributes.
     *
     * @param id the unique identifier for the coach
     * @param firstName the coach's first name
     * @param lastName the coach's last name
     * @param email the coach's email address
     * @param imageId the ID for the coach's image
     * @param specialization the coach's area of specialization
     * @param longitude the geographical longitude of the coach's location
     * @param latitude the geographical latitude of the coach's location
     * @param phoneNumber the coach's phone number
     * @param division the division to which the coach belongs
     */
    public Coach(Long id, String firstName, String lastName, String email, Long imageId, Pillar specialization, Double longitude, Double latitude, String phoneNumber, Division division) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.imageId = imageId;
        this.specialization = specialization;
        this.longitude = longitude;
        this.latitude = latitude;
        this.phoneNumber = phoneNumber;
        this.division = division;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", imageId=" + imageId +
                ", specialization=" + specialization +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", division=" + division +
                '}';
    }
}

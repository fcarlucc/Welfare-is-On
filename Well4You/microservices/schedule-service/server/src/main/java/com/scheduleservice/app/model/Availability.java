package com.scheduleservice.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * Entity representing the availability of a coach on a specific day.
 * Contains information about the coach's ID, the date of availability, and the list of time slots available on that day.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "availability", schema = "scheduleservice")
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coach_id", nullable = false)
    private Long coachId;

    @Column(nullable = false)
    private LocalDate day;

    @OneToMany(mappedBy = "availability", cascade = CascadeType.ALL)
    private List<Slot> slots;

    /**
     * Constructs an instance of {@link Availability} with the specified coach ID, day, and list of slots.
     *
     * @param coachId the ID of the coach
     * @param day     the date of availability
     * @param slots   the list of slots available on that day
     */
    public Availability(Long coachId, LocalDate day, List<Slot> slots) {
        this.coachId = coachId;
        this.day = day;
        this.slots = slots;
    }

    /**
     * Constructs an instance of {@link Availability} with the specified coach ID and day.
     *
     * @param coachId the ID of the coach
     * @param day     the date of availability
     */
    public Availability(Long coachId, LocalDate day) {
        this.coachId = coachId;
        this.day = day;
    }
}

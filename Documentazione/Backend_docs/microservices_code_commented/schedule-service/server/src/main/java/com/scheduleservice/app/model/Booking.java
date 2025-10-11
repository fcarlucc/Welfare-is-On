package com.scheduleservice.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Entity representing a booking made by a user for a specific time slot with a coach.
 * Contains information about the coach's ID, user's ID, booking date, notes, and the associated time slot.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "booking", schema = "scheduleservice")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coach_id", nullable = false)
    private Long coachId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private LocalDate day;

    private String notes;

    @ManyToOne
    @JoinColumn(name = "slot_id")
    private Slot slot;

    /**
     * Constructs an instance of {@link Booking} with the specified coach ID, user ID, day, and slot.
     *
     * @param coachId the ID of the coach
     * @param userId  the ID of the user
     * @param day     the date of the booking
     * @param slot    the associated time slot
     */
    public Booking(Long coachId, Long userId, LocalDate day, Slot slot) {
        this.coachId = coachId;
        this.userId = userId;
        this.day = day;
        this.slot = slot;
    }

    /**
     * Constructs an instance of {@link Booking} with the specified coach ID, user ID, day, slot, and notes.
     *
     * @param coachId the ID of the coach
     * @param userId  the ID of the user
     * @param day     the date of the booking
     * @param slot    the associated time slot
     * @param notes   additional notes for the booking
     */
    public Booking(Long coachId, Long userId, LocalDate day, Slot slot, String notes) {
        this.coachId = coachId;
        this.userId = userId;
        this.day = day;
        this.slot = slot;
        this.notes = notes;
    }
}

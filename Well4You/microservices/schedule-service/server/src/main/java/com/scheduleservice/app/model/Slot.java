package com.scheduleservice.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

/**
 * Entity representing a time slot within a specific availability period of a coach.
 * Contains information about the start and end times of the slot and the associated availability.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "slot", schema = "scheduleservice")
public class Slot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @ManyToOne
    @JoinColumn(name = "availability_id")
    private Availability availability;

    /**
     * Constructs an instance of {@link Slot} with the specified start time, end time, and availability.
     *
     * @param startTime    the start time of the slot
     * @param endTime      the end time of the slot
     * @param availability the associated availability
     */
    public Slot(LocalTime startTime, LocalTime endTime, Availability availability) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.availability = availability;
    }
}

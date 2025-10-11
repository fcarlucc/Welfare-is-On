package com.scheduleservice.app.repository;

import com.scheduleservice.app.model.Availability;
import com.scheduleservice.app.model.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for accessing {@link Slot} entities from the database.
 * Provides methods for retrieving slot data based on specific criteria.
 */
public interface ISlotRepository extends JpaRepository<Slot, Long> {

    /**
     * Finds a {@link Slot} entity by the specified start time, end time, and availability.
     *
     * @param startTime    the start time of the slot
     * @param endTime      the end time of the slot
     * @param availability the availability associated with the slot
     * @return an {@link Optional} containing the found slot, or empty if not found
     */
    Optional<Slot> findByStartTimeAndEndTimeAndAvailability(LocalTime startTime, LocalTime endTime, Availability availability);

    /**
     * Finds all {@link Slot} entities that start before the specified time on the specified day.
     *
     * @param startTime the time before which slots should start
     * @param day       the date of the availability
     * @return a list of all slots that start before the specified time on the specified day
     */
    List<Slot> findAllByStartTimeBeforeAndAvailabilityDay(LocalTime startTime, LocalDate day);

}

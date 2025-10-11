package com.scheduleservice.app.repository;

import com.scheduleservice.app.model.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for accessing {@link Availability} entities from the database.
 * Provides methods for retrieving availability data based on specific criteria.
 */
public interface IAvailabilityRepository extends JpaRepository<Availability, Long> {

    /**
     * Finds an {@link Availability} entity by the specified day and coach ID.
     *
     * @param day     the date of the availability
     * @param coachId the ID of the coach
     * @return an {@link Optional} containing the found availability, or empty if not found
     */
    Optional<Availability> findByDayAndCoachId(LocalDate day, Long coachId);

    /**
     * Finds all {@link Availability} entities for the specified coach ID.
     *
     * @param coachId the ID of the coach
     * @return a list of all availabilities for the specified coach
     */
    List<Availability> findAllByCoachId(Long coachId);

    /**
     * Finds all {@link Availability} entities where the day is before the specified date.
     *
     * @param date the date to compare against
     * @return a list of all availabilities before the specified date
     */
    List<Availability> findAllByDayBefore(LocalDate date);
}

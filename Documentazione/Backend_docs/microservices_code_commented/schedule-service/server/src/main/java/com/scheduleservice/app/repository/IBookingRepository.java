package com.scheduleservice.app.repository;

import com.scheduleservice.app.model.Booking;
import com.scheduleservice.app.model.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for accessing {@link Booking} entities from the database.
 * Provides methods for retrieving booking data based on specific criteria.
 */
public interface IBookingRepository extends JpaRepository<Booking, Long> {

    /**
     * Finds a {@link Booking} entity by the specified coach ID, day, and user ID.
     *
     * @param coachId the ID of the coach
     * @param day     the date of the booking
     * @param userId  the ID of the user
     * @return an {@link Optional} containing the found booking, or empty if not found
     */
    Optional<Booking> findByCoachIdAndDayAndUserId(Long coachId, LocalDate day, Long userId);

    /**
     * Finds all {@link Booking} entities for the specified coach ID and day.
     *
     * @param coachId the ID of the coach
     * @param day     the date of the booking
     * @return a list of all bookings for the specified coach on the specified day
     */
    List<Booking> findAllByCoachIdAndDay(Long coachId, LocalDate day);

    /**
     * Finds a {@link Booking} entity by the specified {@link Slot}.
     *
     * @param slot the slot associated with the booking
     * @return an {@link Optional} containing the found booking, or empty if not found
     */
    Optional<Booking> findBySlot(Slot slot);

    /**
     * Finds all {@link Booking} entities for the specified coach ID.
     *
     * @param coachId the ID of the coach
     * @return a list of all bookings for the specified coach
     */
    List<Booking> findAllByCoachId(Long coachId);

    /**
     * Finds all {@link Booking} entities for the specified user ID.
     *
     * @param userId the ID of the user
     * @return a list of all bookings for the specified user
     */
    List<Booking> findAllByUserId(Long userId);
}

package com.scheduleservice.app.service;

import com.scheduleservice.app.dto.SlotDto;
import com.scheduleservice.app.exception.BookingNotFoundException;
import com.scheduleservice.app.exception.SlotDurationWrongException;
import com.scheduleservice.app.exception.SlotNotFoundException;
import com.scheduleservice.app.model.Availability;
import com.scheduleservice.app.model.Booking;
import com.scheduleservice.app.model.Slot;
import com.scheduleservice.app.repository.ISlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Service class for managing time slots for bookings.
 * Provides methods for CRUD operations on slots, as well as validation and cleanup tasks.
 */
@Service
public class SlotService {

	private final ISlotRepository slotRepository;
	private final BookingService bookingService;

	public SlotService(ISlotRepository slotRepository, @Lazy BookingService bookingService) {
		this.slotRepository = slotRepository;
		this.bookingService = bookingService;
	}

	/**
	 * Retrieves all slots.
	 *
	 * @return a list of all available slots
	 */
	public List<Slot> getAll() {
		return slotRepository.findAll();
	}

	/**
	 * Retrieves a slot by its ID.
	 *
	 * @param id the ID of the slot
	 * @return the found slot
	 * @throws SlotNotFoundException if the slot is not found
	 */
	public Slot getById(Long id) {
		return slotRepository.findById(id)
				.orElseThrow(() -> new SlotNotFoundException("Slot not found"));
	}

	/**
	 * Retrieves a slot by its start time, end time, and associated availability.
	 *
	 * @param startTime    the start time of the slot
	 * @param endTime      the end time of the slot
	 * @param availability the availability associated with the slot
	 * @return the found slot
	 * @throws SlotNotFoundException if the slot is not found
	 */
	public Slot getByStartTimeAndEndTimeAndAvailability(LocalTime startTime, LocalTime endTime, Availability availability) {
		return slotRepository.findByStartTimeAndEndTimeAndAvailability(startTime, endTime, availability)
				.orElseThrow(() -> new SlotNotFoundException("Slot not found"));
	}

	/**
	 * Creates a new slot.
	 *
	 * @param slot the slot to create
	 * @return the created slot
	 */
	public Slot create(Slot slot) {
		return slotRepository.save(slot);
	}

	/**
	 * Updates an existing slot.
	 *
	 * @param slot the slot to update
	 * @return the updated slot
	 */
	public Slot update(Slot slot) {
		return slotRepository.save(slot);
	}

	/**
	 * Deletes a slot by its ID.
	 *
	 * @param id the ID of the slot to delete
	 * @return {@code true} if the slot was found and deleted, {@code false} otherwise
	 */
	public Boolean delete(Long id) {
		if (slotRepository.existsById(id)) {
			slotRepository.deleteById(id);
			return true;
		}
		return false;
	}

	/**
	 * Deletes slots that are not booked from the provided list of slots.
	 *
	 * @param slots      the list of all slots
	 * @param bookedSlots the list of booked slots
	 */
	public void deleteNotBookedSlots(List<Slot> slots, List<Slot> bookedSlots) {
		List<Long> bookedSlotIds = bookedSlots.stream()
				.map(Slot::getId)
				.toList();

		slots.stream()
				.filter(slot -> !bookedSlotIds.contains(slot.getId()))
				.forEach(slot -> {
					System.out.println("Deleting not booked slot " + slot.getId());
					delete(slot.getId());
				});
	}

	/**
	 * Validates that each slot in the provided list has a duration of 30 minutes.
	 *
	 * @param slots the list of slot DTOs to validate
	 * @throws SlotDurationWrongException if any slot does not have a duration of 30 minutes
	 */
	public void checkSlotsDurability(List<SlotDto> slots) {
		slots.forEach(slot -> {
			Duration duration = Duration.between(slot.getStartTime(), slot.getEndTime());
			if (duration.toMinutes() != 30) {
				throw new SlotDurationWrongException("Slot duration must be 30 minutes");
			}
		});
	}

	/**
	 * Checks if any of the provided slots overlap with already booked slots.
	 *
	 * @param slots      the list of slots to check
	 * @param bookedSlots the list of already booked slots
	 * @return {@code true} if there is any overlap, {@code false} otherwise
	 */
	public boolean areSlotsAlreadyBooked(List<SlotDto> slots, List<Slot> bookedSlots) {
		return slots.stream()
				.anyMatch(slot -> bookedSlots.stream()
						.anyMatch(bookedSlot -> timeOverlaps(bookedSlot, slot.getStartTime(), slot.getEndTime())));
	}

	/**
	 * Determines if the specified time range overlaps with the given slot.
	 *
	 * @param bookedSlot the slot to check against
	 * @param startTime  the start time of the range to check
	 * @param endTime    the end time of the range to check
	 * @return {@code true} if the times overlap, {@code false} otherwise
	 */
	private boolean timeOverlaps(Slot bookedSlot, LocalTime startTime, LocalTime endTime) {
		return !(endTime.isBefore(bookedSlot.getStartTime()) || startTime.isAfter(bookedSlot.getEndTime()));
	}

	/**
	 * Deletes all slots that have expired, i.e., those starting before the current time and date.
	 * Also removes any associated bookings.
	 */
	@Transactional
	public void deleteExpiredSlots() {
		LocalTime currentHours = LocalTime.now();
		LocalDate currentDay = LocalDate.now();
		List<Slot> expiredSlots = slotRepository.findAllByStartTimeBeforeAndAvailabilityDay(currentHours, currentDay);

		expiredSlots.forEach(slot -> {
			try {
				Booking booking = bookingService.getBySlot(slot);
				bookingService.delete(booking.getId());
			} catch (BookingNotFoundException ignored) {
			}
			slotRepository.delete(slot);
		});
	}
}

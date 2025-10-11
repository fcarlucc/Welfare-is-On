package com.scheduleservice.app.service;

import com.scheduleservice.app.dto.AvailabilityDto;
import com.scheduleservice.app.dto.SlotDto;
import com.scheduleservice.app.exception.AvailabilityNotFoundException;
import com.scheduleservice.app.exception.AvailabilitySetFailureException;
import com.scheduleservice.app.exception.BookingNotFoundException;
import com.scheduleservice.app.mapper.ScheduleMapper;
import com.scheduleservice.app.model.Availability;
import com.scheduleservice.app.model.Booking;
import com.scheduleservice.app.model.Slot;
import com.scheduleservice.app.repository.IAvailabilityRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service class for managing availability of coaches.
 * Provides methods for creating, updating, retrieving, and deleting availabilities.
 */
@Service
public class AvailabilityService {

	private final IAvailabilityRepository availabilityRepository;
	private final ScheduleMapper scheduleMapper;
	private final BookingService bookingService;
	private final SlotService slotService;

	/**
	 * Constructs an instance of {@link AvailabilityService}.
	 *
	 * @param availabilityRepository the availability repository
	 * @param scheduleMapper         the schedule mapper
	 * @param bookingService         the booking service
	 * @param slotService            the slot service
	 */
	public AvailabilityService(IAvailabilityRepository availabilityRepository, ScheduleMapper scheduleMapper, @Lazy BookingService bookingService, SlotService slotService) {
		this.availabilityRepository = availabilityRepository;
		this.scheduleMapper = scheduleMapper;
		this.bookingService = bookingService;
		this.slotService = slotService;
	}

	/**
	 * Retrieves all availabilities.
	 *
	 * @return a list of all availabilities
	 */
	public List<Availability> getAll() {
		return availabilityRepository.findAll();
	}

	/**
	 * Retrieves an availability by its ID.
	 *
	 * @param id the ID of the availability
	 * @return the found availability
	 * @throws AvailabilityNotFoundException if the availability is not found
	 */
	public Availability getById(Long id) {
		Optional<Availability> availability = availabilityRepository.findById(id);
		if (availability.isEmpty()) {
			throw new AvailabilityNotFoundException("Availability id not found");
		}
		return availability.get();
	}

	/**
	 * Retrieves an availability by the specified day and coach ID.
	 *
	 * @param day     the day of the availability
	 * @param coachId the ID of the coach
	 * @return the found availability
	 * @throws AvailabilityNotFoundException if the availability is not found
	 */
	public Availability getByDayAndCoachId(LocalDate day, Long coachId) {
		Optional<Availability> availability = availabilityRepository.findByDayAndCoachId(day, coachId);
		if (availability.isEmpty()) {
			throw new AvailabilityNotFoundException("Availability not found");
		}
		return availability.get();
	}

	/**
	 * Creates a new availability.
	 *
	 * @param availability the availability to create
	 * @return the created availability
	 */
	public Availability create(Availability availability) {
		return availabilityRepository.save(availability);
	}

	/**
	 * Updates an existing availability.
	 *
	 * @param availability the availability to update
	 * @return the updated availability
	 */
	public Availability update(Availability availability) {
		return availabilityRepository.save(availability);
	}

	/**
	 * Deletes an availability by its ID.
	 *
	 * @param id the ID of the availability to delete
	 * @return true if the availability was found and deleted, false otherwise
	 */
	public Boolean delete(Long id) {
		Optional<Availability> foundAvailability = availabilityRepository.findById(id);
		if (foundAvailability.isEmpty()) {
			return false;
		}
		availabilityRepository.delete(foundAvailability.get());
		return true;
	}

	/**
	 * Sets the availability based on the provided {@link AvailabilityDto}.
	 *
	 * @param availabilityDto the availability DTO
	 * @throws AvailabilitySetFailureException if there are already booked slots in the provided availability
	 */
	@Transactional
	public void setAvailability(AvailabilityDto availabilityDto) {
		slotService.checkSlotsDurability(availabilityDto.getSlots());
		List<Slot> bookedSlots = new ArrayList<>();

		try {
			Availability availability = getByDayAndCoachId(availabilityDto.getDay(), availabilityDto.getCoachId());
			List<Booking> todayBookings = bookingService.getAllByCoachIdAndDay(availabilityDto.getCoachId(), availability.getDay());

			if (!todayBookings.isEmpty()) {
				bookedSlots = new ArrayList<>();
				for (Booking booking : todayBookings) {
					bookedSlots.add(booking.getSlot());
				}
				if (slotService.areSlotsAlreadyBooked(availabilityDto.getSlots(), bookedSlots))
					throw new AvailabilitySetFailureException("In the slots that you are setting, there are hours already booked");
			}

			slotService.deleteNotBookedSlots(availability.getSlots(), bookedSlots);
			if (!bookedSlots.isEmpty()) {
				availability.setSlots(combineUniqueSlots(bookedSlots, scheduleMapper.listSlotsDtoToListSlots(availabilityDto.getSlots(), availability)));
			} else {
				availability.setSlots(scheduleMapper.listSlotsDtoToListSlots(availabilityDto.getSlots(), availability));
			}
			update(availability);
			return;
		} catch (AvailabilityNotFoundException ignored) {
		}

		Availability availability = new Availability(availabilityDto.getCoachId(), availabilityDto.getDay());
		List<Slot> slots = scheduleMapper.listSlotsDtoToListSlots(availabilityDto.getSlots(), availability);
		availability.setSlots(slots);
		create(availability);
	}

	/**
	 * Combines two lists of slots, removing duplicates.
	 *
	 * @param list1 the first list of slots
	 * @param list2 the second list of slots
	 * @return a combined list of unique slots
	 */
	public static List<Slot> combineUniqueSlots(List<Slot> list1, List<Slot> list2) {
		Stream<Slot> combinedStream = Stream.concat(list1.stream(), list2.stream());
		return combinedStream.distinct().collect(Collectors.toList());
	}

	/**
	 * Retrieves a list of not booked slots for a specific day and coach.
	 *
	 * @param day     the day to check for not booked slots
	 * @param coachId the ID of the coach
	 * @return a list of not booked slots
	 */
	@Transactional
	public List<SlotDto> getNotBookedSlots(LocalDate day, Long coachId) {
		boolean isBooked;
		List<SlotDto> result = new ArrayList<>();

		Availability availability;
		try {
			availability = getByDayAndCoachId(day, coachId);
		} catch (AvailabilityNotFoundException e) {
			return result;
		}

		List<SlotDto> bookedSlots = bookingService.getBookedSlots(day, coachId);

		for (Slot slot : availability.getSlots()) {
			isBooked = false;
			for (SlotDto bookedSlot : bookedSlots) {
				if (bookedSlot.getEndTime().equals(slot.getEndTime()) || bookedSlot.getStartTime().equals(slot.getStartTime())) {
					isBooked = true;
					break;
				}
			}
			if (!isBooked) {
				result.add(new SlotDto(slot.getStartTime(), slot.getEndTime()));
			}
		}

		return result;
	}

	/**
	 * Retrieves a list of available days for a specific coach.
	 *
	 * @param coachId the ID of the coach
	 * @return a list of available days
	 */
	@Transactional
	public List<LocalDate> getAvailableDays(Long coachId) {
		List<LocalDate> availableDays = new ArrayList<>();
		List<Availability> allAvailability = availabilityRepository.findAllByCoachId(coachId);

		for (Availability availability : allAvailability) {
			availableDays.add(availability.getDay());
		}

		return availableDays;
	}

	/**
	 * Deletes expired availabilities.
	 * Also deletes associated slots and bookings if they exist.
	 */
	@Transactional
	public void deleteExpiredAvailabilities() {
		LocalDate currentDay = LocalDate.now();
		List<Availability> expiredAvailabilities = availabilityRepository.findAllByDayBefore(currentDay);

		for (Availability availability : expiredAvailabilities) {
			for (Slot slot : availability.getSlots()) {
				try {
					Booking booking = bookingService.getBySlot(slot);
					bookingService.delete(booking.getId());
				} catch (BookingNotFoundException ignored) {
				}
				slotService.delete(slot.getId());
			}
			delete(availability.getId());
		}
	}
}

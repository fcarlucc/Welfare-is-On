package com.scheduleservice.app.service;

import com.scheduleservice.app.dto.*;
import com.scheduleservice.app.exception.AvailabilityNotFoundException;
import com.scheduleservice.app.exception.BookingFailureException;
import com.scheduleservice.app.exception.BookingNotFoundException;
import com.scheduleservice.app.exception.SlotNotFoundException;
import com.scheduleservice.app.mapper.ScheduleMapper;
import com.scheduleservice.app.model.Availability;
import com.scheduleservice.app.model.Booking;
import com.scheduleservice.app.model.Slot;
import com.scheduleservice.app.repository.IBookingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing bookings.
 * Provides methods for creating, updating, retrieving, and deleting bookings.
 */
@Service
@RequiredArgsConstructor
public class BookingService {

	@Value("${user.service.name}")
	private String userServiceIp;

	@Value("${user.service.port}")
	private String userServicePort;

	@Value("${coach.service.name}")
	private String coachServiceIp;

	@Value("${coach.service.port}")
	private String coachServicePort;

	private final IBookingRepository bookingRepository;
	private final SlotService slotService;
	private final AvailabilityService availabilityService;
	private final ScheduleMapper scheduleMapper;
	private final EmailService emailService;
	private final WebClient webClient;

	/**
	 * Retrieves all bookings.
	 *
	 * @return a list of all bookings
	 */
	public List<Booking> getAll() {
		return bookingRepository.findAll();
	}

	/**
	 * Retrieves a booking by its ID.
	 *
	 * @param id the ID of the booking
	 * @return the found booking
	 * @throws BookingNotFoundException if the booking is not found
	 */
	public Booking getById(Long id) {
		Optional<Booking> booking = bookingRepository.findById(id);
		if (booking.isEmpty()) {
			throw new BookingNotFoundException("Booking not found");
		}
		return booking.get();
	}

	/**
	 * Retrieves a booking by coach ID, day, and user ID.
	 *
	 * @param coachId the ID of the coach
	 * @param day     the date of the booking
	 * @param userId  the ID of the user
	 * @return the found booking
	 * @throws BookingNotFoundException if the booking is not found
	 */
	public Booking getByCoachIdAndDayAndUserId(Long coachId, LocalDate day, Long userId) {
		Optional<Booking> booking = bookingRepository.findByCoachIdAndDayAndUserId(coachId, day, userId);
		if (booking.isEmpty()) {
			throw new BookingNotFoundException("Booking not found");
		}
		return booking.get();
	}

	/**
	 * Retrieves a booking by slot.
	 *
	 * @param slot the slot associated with the booking
	 * @return the found booking
	 * @throws BookingNotFoundException if the booking is not found
	 */
	public Booking getBySlot(Slot slot) {
		Optional<Booking> booking = bookingRepository.findBySlot(slot);
		if (booking.isEmpty()) {
			throw new BookingNotFoundException("Booking not found");
		}
		return booking.get();
	}

	/**
	 * Retrieves all bookings by coach ID and day.
	 *
	 * @param coachId the ID of the coach
	 * @param day     the date of the booking
	 * @return a list of all bookings for the specified coach on the specified day
	 */
	public List<Booking> getAllByCoachIdAndDay(Long coachId, LocalDate day) {
		return bookingRepository.findAllByCoachIdAndDay(coachId, day);
	}

	/**
	 * Creates a new booking.
	 *
	 * @param booking the booking to create
	 * @return the created booking
	 */
	public Booking create(Booking booking) {
		return bookingRepository.save(booking);
	}

	/**
	 * Updates an existing booking.
	 *
	 * @param booking the booking to update
	 * @return the updated booking
	 */
	public Booking update(Booking booking) {
		return bookingRepository.save(booking);
	}

	/**
	 * Deletes a booking by its ID.
	 *
	 * @param id the ID of the booking to delete
	 * @return true if the booking was found and deleted, false otherwise
	 */
	public Boolean delete(Long id) {
		Optional<Booking> foundBooking = bookingRepository.findById(id);
		if (foundBooking.isEmpty()) {
			return false;
		}
		bookingRepository.delete(foundBooking.get());
		return true;
	}



	/**
	 * Sets a new booking based on the provided {@link BookingDto}.
	 *
	 * @param bookingDto the booking DTO
	 * @throws BookingFailureException if the booking fails due to already booked slots or other issues
	 */
	@Transactional
	public void setBooking(BookingDto bookingDto) {

		Slot slot = null;
		//verify if the booking is already done today and if the slots you are booking are already booked
		//to verify id the booking is already done today by you
		try {
			getByCoachIdAndDayAndUserId(bookingDto.getCoachId(), bookingDto.getDay(), bookingDto.getUserId());
			throw new BookingFailureException("Booking Already done Today");
		} catch (BookingNotFoundException ignored) {
		}

		//to verify if the slot is already booked
		try {
			Availability availability = availabilityService.getByDayAndCoachId(bookingDto.getDay(), bookingDto.getCoachId());
			slot = slotService.getByStartTimeAndEndTimeAndAvailability(bookingDto.getSlot().getStartTime(), bookingDto.getSlot().getEndTime(), availability);
			getBySlot(slot);
			throw new BookingFailureException("Slot already booked");
		} catch (AvailabilityNotFoundException e) {
			throw new BookingFailureException("Slot that you want to book was not set by the coach");
		}
		catch (SlotNotFoundException e) {
			throw new BookingFailureException("Slot that you want to book does not exist");
		}
		catch (BookingNotFoundException e) {
			//here we can save the new booking
			Booking booking;

			if (bookingDto.getNotes() != null) {
				booking = new Booking(bookingDto.getCoachId(), bookingDto.getUserId(), bookingDto.getDay(), slot, bookingDto.getNotes());
			} else {
				booking = new Booking(bookingDto.getCoachId(), bookingDto.getUserId(), bookingDto.getDay(), slot);
			}
			create(booking);
			String userEmail = getUserEmail(booking.getUserId());
			String coachEmail = getCoachEmail(booking.getCoachId());
			emailService.sendEmail(userEmail, "you have booked successfully your meet with the coach this is your link to join at " + bookingDto.getSlot().getStartTime().toString() + "\n\n", "Call booked");
			emailService.sendEmail(coachEmail, "you have been booked by " + userEmail + " the meet will start at " + bookingDto.getSlot().getStartTime().toString() + "\n\n", "Call booked");
		}
	}

	/**
	 * Retrieves a list of booked slots for a specific day and coach.
	 *
	 * @param day     the day to check for booked slots
	 * @param coachId the ID of the coach
	 * @return a list of booked slots
	 */
	@Transactional
	public List<SlotDto> getBookedSlots(LocalDate day, Long coachId) {
		List<SlotDto> result = new ArrayList<>();
		List<Booking> todayBookings = getAllByCoachIdAndDay(coachId, day);

		for (Booking booking : todayBookings) {
			result.add(new SlotDto(booking.getSlot().getStartTime(), booking.getSlot().getEndTime()));
		}

		return result;
	}

	/**
	 * Retrieves a list of bookings for a specific coach.
	 *
	 * @param coachId the ID of the coach
	 * @return a list of booking response DTOs
	 */
	@Transactional
	public List<BookingResponseDto> getBookings(Long coachId) {
		List<Booking> bookings = bookingRepository.findAllByCoachId(coachId);
		return bookings.stream()
				.map(scheduleMapper::bookingToBookingResponseDto)
				.toList();
	}

	/**
	 * Retrieves a list of bookings for a specific user.
	 *
	 * @param userId the ID of the user
	 * @return a list of booking response DTOs
	 */
	public List<BookingResponseDto> getBookingsUser(Long userId) {
		List<Booking> bookings = bookingRepository.findAllByUserId(userId);
		return bookings.stream()
				.map(scheduleMapper::bookingToBookingResponseDto)
				.toList();
	}

	/**
	 * Retrieves the email of a user by their ID.
	 *
	 * @param userId the ID of the user
	 * @return the email of the user, or null if not found
	 */
	public String getUserEmail(Long userId) {
		UserInfoDto userInfoDto;
		try {
			userInfoDto = webClient
					.get()
					.uri("http://" + userServiceIp + ":" + userServicePort + "/api/user/info",
							uriBuilder -> uriBuilder.queryParam("userId", userId).build())
					.retrieve()
					.bodyToMono(UserInfoDto.class)
					.block();
			return userInfoDto.getEmail();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Retrieves the email of a coach by their ID.
	 *
	 * @param coachId the ID of the coach
	 * @return the email of the coach, or null if not found
	 */
	public String getCoachEmail(Long coachId) {
		AggregatedCoachDto aggregatedCoachDto;
		try {
			aggregatedCoachDto = webClient
					.get()
					.uri("http://" + coachServiceIp + ":" + coachServicePort + "/api/coach/get-coach",
							uriBuilder -> uriBuilder.queryParam("coachId", coachId).build())
					.retrieve()
					.bodyToMono(AggregatedCoachDto.class)
					.block();
			return aggregatedCoachDto.getEmail();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
}


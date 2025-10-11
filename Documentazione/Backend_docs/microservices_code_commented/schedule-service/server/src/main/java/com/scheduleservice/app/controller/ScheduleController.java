package com.scheduleservice.app.controller;

import com.scheduleservice.app.dto.*;
import com.scheduleservice.app.service.AvailabilityService;
import com.scheduleservice.app.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * REST controller for handling schedule-related operations.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule")
public class ScheduleController {

    private final AvailabilityService availabilityService;
    private final BookingService bookingService;

    /**
     * Endpoint to set availability for coaching sessions.
     *
     * @param availabilityDto DTO containing availability details
     * @return ResponseEntity with a success message if the operation is successful
     */
    @PostMapping("/set-availability")
    public ResponseEntity<ResponseDto> setAvailability(@RequestBody @Valid AvailabilityDto availabilityDto) {
        availabilityService.setAvailability(availabilityDto);
        return new ResponseEntity<>(new ResponseDto("Availability set successful"), HttpStatus.OK);
    }

    /**
     * Endpoint to make a booking for a coaching session.
     *
     * @param bookingDto DTO containing booking details
     * @return ResponseEntity with a success message if the operation is successful
     */
    @PostMapping("/set-booking")
    public ResponseEntity<ResponseDto> setBooking(@RequestBody @Valid BookingDto bookingDto) {
        bookingService.setBooking(bookingDto);
        return new ResponseEntity<>(new ResponseDto("Success"), HttpStatus.OK);
    }

    /**
     * Endpoint to get booked slots for a specific day and coach.
     *
     * @param day     date for which to retrieve booked slots
     * @param coachId ID of the coach
     * @return ResponseEntity containing a list of SlotDto representing booked slots
     */
    @GetMapping("/booked-slots")
    public ResponseEntity<List<SlotDto>> getBookedSlots(@RequestParam LocalDate day, @RequestParam Long coachId) {
        List<SlotDto> bookedSlots = bookingService.getBookedSlots(day, coachId);
        return new ResponseEntity<>(bookedSlots, HttpStatus.OK);
    }

    /**
     * Endpoint to get available slots for a specific day and coach.
     *
     * @param day     date for which to retrieve available slots
     * @param coachId ID of the coach
     * @return ResponseEntity containing a list of SlotDto representing available slots
     */
    @GetMapping("/not-booked-slots")
    public ResponseEntity<List<SlotDto>> getDaySlots(@RequestParam LocalDate day, @RequestParam Long coachId) {
        List<SlotDto> availableSlots = availabilityService.getNotBookedSlots(day, coachId);
        return new ResponseEntity<>(availableSlots, HttpStatus.OK);
    }

    /**
     * Endpoint to get all available days by a coach for a user.
     *
     * @param coachId ID of the coach
     * @return ResponseEntity containing a list of LocalDate representing available days
     */
    @GetMapping("/get-available-days-coach")
    public ResponseEntity<List<LocalDate>> getAvailableDaysCoach(@RequestParam Long coachId) {
        List<LocalDate> availableDays = availabilityService.getAvailableDays(coachId);
        return new ResponseEntity<>(availableDays, HttpStatus.OK);
    }

    /**
     * Endpoint to get all bookings made by a specific coach.
     *
     * @param coachId ID of the coach
     * @return ResponseEntity containing a list of BookingResponseDto representing bookings
     */
    @GetMapping("/get-bookings")
    public ResponseEntity<List<BookingResponseDto>> getBookings(@RequestParam Long coachId) {
        List<BookingResponseDto> bookings = bookingService.getBookings(coachId);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    /**
     * Endpoint to get all bookings made by a specific user.
     *
     * @param userId ID of the user
     * @return ResponseEntity containing a list of BookingResponseDto representing bookings
     */
    @GetMapping("/get-bookings-user")
    public ResponseEntity<List<BookingResponseDto>> getBookingsUser(@RequestParam Long userId) {
        List<BookingResponseDto> bookings = bookingService.getBookingsUser(userId);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

}

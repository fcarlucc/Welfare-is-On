package com.scheduleservice.app.mapper;

import com.scheduleservice.app.dto.BookingResponseDto;
import com.scheduleservice.app.dto.SlotDto;
import com.scheduleservice.app.model.Availability;
import com.scheduleservice.app.model.Booking;
import com.scheduleservice.app.model.Slot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class for converting between entity models and DTOs related to scheduling.
 */
@Component
@RequiredArgsConstructor
public class ScheduleMapper {

    /**
     * Converts a list of {@link SlotDto} to a list of {@link Slot} entities.
     *
     * @param slots        the list of {@link SlotDto} to convert
     * @param availability the availability associated with the slots
     * @return a list of {@link Slot} entities
     */
    public List<Slot> listSlotsDtoToListSlots(List<SlotDto> slots, Availability availability) {
        List<Slot> slotList = new ArrayList<>();

        for (SlotDto slotDto : slots) {
            System.out.println(slotDto);
            slotList.add(slotDtoToSlot(slotDto, availability));
            System.out.println(slotDtoToSlot(slotDto, availability).getStartTime());
        }

        return slotList;
    }

    /**
     * Converts a {@link SlotDto} to a {@link Slot} entity.
     *
     * @param slotDto      the {@link SlotDto} to convert
     * @param availability the availability associated with the slot
     * @return the {@link Slot} entity
     */
    public Slot slotDtoToSlot(SlotDto slotDto, Availability availability) {
        return new Slot(
                slotDto.getStartTime(),
                slotDto.getEndTime(),
                availability
        );
    }

    /**
     * Converts a {@link Booking} entity to a {@link BookingResponseDto}.
     *
     * @param booking the {@link Booking} to convert
     * @return the {@link BookingResponseDto}
     */
    public BookingResponseDto bookingToBookingResponseDto(Booking booking) {
        Duration duration = Duration.between(booking.getSlot().getStartTime(), booking.getSlot().getEndTime());
        long minutes = duration.toMinutes();

        return new BookingResponseDto(
                booking.getDay(),
                new SlotDto(booking.getSlot().getStartTime(), booking.getSlot().getEndTime()),
                minutes,
                booking.getNotes());
    }
}

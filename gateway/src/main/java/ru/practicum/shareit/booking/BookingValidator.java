package ru.practicum.shareit.booking;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.BadParameterException;

import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class BookingValidator {

    public void checkDto(BookItemRequestDto bookingDto) {
        LocalDateTime start = bookingDto.getStart();
        LocalDateTime end = bookingDto.getEnd();
        if (Objects.isNull(start)) {
            throw new BadParameterException("start date of booking can't be null");
        }
        if (Objects.isNull(end)) {
            throw new BadParameterException("end date of booking can't be null");
        }
        if (Objects.equals(end, start)) {
            throw new BadParameterException("Booking end equals booking start");
        }
        if (end.isBefore(start)) {
            throw new BadParameterException("Booking end is before booking start");
        }
    }

    public void checkApproved(Boolean approved) {
        if (Objects.isNull(approved)) {
            throw new BadParameterException("Field of booking approving can't be null");
        }
    }
}

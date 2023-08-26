package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

@Component
@AllArgsConstructor
public class BookingMapper {

    public Booking mapToNewBooking(BookingDto bookingDto, User booker, Item item) {
        Booking booking = new Booking();
        booking.setStart(bookingDto.getStart());
        booking.setEnd(bookingDto.getEnd());
        booking.setItem(item);
        booking.setStatus(Status.WAITING);
        booking.setBooker(booker);
        return booking;
    }

}
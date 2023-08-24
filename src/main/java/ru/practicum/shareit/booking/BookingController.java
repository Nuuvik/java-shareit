package ru.practicum.shareit.booking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


@RestController
@RequestMapping(path = "/bookings")
@Slf4j
@Validated
public class BookingController {
    private static final String USER_ID_HEADER = "X-Sharer-User-Id";
    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping()
    public Booking create(@RequestBody @Valid BookingDto bookingDto, @RequestHeader(USER_ID_HEADER) Long userId) {
        log.info("request received POST /bookings");
        return bookingService.saveBooking(bookingDto, userId);
    }

    @PatchMapping("/{bookingId}")
    public Booking update(@PathVariable @NotNull Long bookingId, @RequestHeader(USER_ID_HEADER) Long userId, @RequestParam Boolean approved) {
        log.debug("request received PATCH /bookings");
        return bookingService.approve(bookingId, userId, approved);
    }

    @GetMapping("/{bookingId}")
    public Booking getBookingById(@PathVariable @NotNull Long bookingId, @RequestHeader(USER_ID_HEADER) Long userId) {
        log.info("request received GET /bookings");
        return bookingService.getBookingById(bookingId, userId);
    }

    @GetMapping()
    public List<Booking> getBookings(@RequestHeader(USER_ID_HEADER) Long userId, @RequestParam(defaultValue = "ALL") String state) {
        log.info("request received GET /bookings");
        return bookingService.getBookings(userId, state);
    }

    @GetMapping("/owner")
    public List<Booking> getBookingsByOwner(@RequestHeader(USER_ID_HEADER) Long userId, @RequestParam(defaultValue = "ALL") String state) {
        log.info("request received GET /bookings/owner");
        return bookingService.getBookingsByOwner(userId, state);
    }
}
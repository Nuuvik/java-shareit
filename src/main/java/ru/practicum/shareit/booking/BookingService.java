package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.exceptions.UnknownStateException;
import ru.practicum.shareit.exceptions.WrongEntityException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ItemService itemService;
    private final UserService userService;
    private final BookingMapper bookingMapper;

    @Transactional(readOnly = true)
    public List<Booking> getBookings() {
        return bookingRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Boolean findBookingById(Long bookingId) {
        return bookingRepository.existsById(bookingId);
    }

    @Transactional(readOnly = true)
    public Optional<Booking> getBookingsById(Long bookingsId) {
        if (!bookingRepository.existsById(bookingsId)) {
            throw new NotFoundException();
        }
        return bookingRepository.findById(bookingsId);
    }

    public Booking saveBooking(BookingDto bookingDto, Long id) {
        Item item = itemService.getItemById(bookingDto.getItemId());
        User user = userService.getUsersById(id).get();

        if (!(bookingDto.getEnd().isAfter(bookingDto.getStart()))) {
            throw new WrongEntityException("End of the booking can't be early than start");
        }

        Booking booking = bookingMapper.mapToNewBooking(bookingDto, user, item);

        if (!itemService.findUserById(id)) {
            throw new NotFoundException();
        }
        if (!item.getAvailable()) {
            throw new WrongEntityException();
        }
        if (item.getOwner().equals(id)) {
            throw new NotFoundException();
        }
        bookingRepository.save(booking);
        return booking;
    }

    @Transactional(readOnly = true)
    public Booking getBookingById(Long bookingId, Long userId) {
        if ((!bookingRepository.existsById(bookingId)) || (!itemService.findUserById(userId))) {
            throw new NotFoundException();
        }
        Booking booking = bookingRepository.findById(bookingId).get();
        if (!((Objects.equals(booking.getBooker().getId(), userId)) || (Objects.equals(booking.getItem().getOwner(), userId)))) {
            throw new NotFoundException();
        }
        return booking;
    }

    @Transactional(readOnly = true)
    public List<Booking> getBookings(Long userId, String state) {
        if (!itemService.findUserById(userId)) {
            throw new NotFoundException();
        }
        switch (state) {
            case "ALL": {
                return bookingRepository.findByBookerIdOrderByIdDesc(userId);
            }
            case "CURRENT": {
                return bookingRepository.findByBookerIdAndEndAfterAndStartBeforeOrderByStartDesc(userId, LocalDateTime.now(), LocalDateTime.now());
            }
            case "PAST": {
                return bookingRepository.findByBookerIdAndEndBeforeOrderByStartDesc(userId, LocalDateTime.now());
            }
            case "FUTURE": {
                return bookingRepository.findByBookerIdAndStartAfterOrderByStartDesc(userId, LocalDateTime.now());
            }
            case "WAITING": {
                return bookingRepository.findByBookerIdAndStatusOrderByStartDesc(userId, Status.WAITING);
            }
            case "REJECTED": {
                return bookingRepository.findByBookerIdAndStatusOrderByStartDesc(userId, Status.REJECTED);
            }
            default:
                throw new UnknownStateException("Unknown state: UNSUPPORTED_STATUS");
        }
    }

    @Transactional(readOnly = true)
    public List<Booking> getBookingsByOwner(Long userId, String state) {
        if (!itemService.findUserById(userId)) {
            throw new NotFoundException();
        }
        switch (state) {
            case "ALL": {
                return bookingRepository.findByItemOwnerOrderByIdDesc(userId);
            }
            case "CURRENT": {
                return bookingRepository.findByItemOwnerAndEndAfterAndStartBeforeOrderByStartDesc(userId, LocalDateTime.now(), LocalDateTime.now());
            }
            case "PAST": {
                return bookingRepository.findByItemOwnerAndEndBeforeOrderByStartDesc(userId, LocalDateTime.now());
            }
            case "FUTURE": {
                return bookingRepository.findByItemOwnerAndStartAfterOrderByStartDesc(userId, LocalDateTime.now());
            }
            case "WAITING": {
                return bookingRepository.findByItemOwnerAndStatusOrderByStartDesc(userId, Status.WAITING);
            }
            case "REJECTED": {
                return bookingRepository.findByItemOwnerAndStatusOrderByStartDesc(userId, Status.REJECTED);
            }
            default:
                throw new UnknownStateException("Unknown state: UNSUPPORTED_STATUS");
        }
    }

    public Booking approve(Long bookingId, Long userId, Boolean bool) {
        if ((!userService.isExistUserById(userId)) || (!bookingRepository.existsById(bookingId)) || (!Objects.equals(bookingRepository.findById(bookingId).get().getItem().getOwner(), userId))) {
            throw new NotFoundException();
        }
        Booking booking = bookingRepository.findById(bookingId).get();
        if (booking.getStatus().equals(Status.APPROVED)) {
            throw new WrongEntityException();
        }
        if (bool) {
            booking.setStatus(Status.APPROVED);
        } else booking.setStatus(Status.REJECTED);
        return bookingRepository.save(booking);
    }
}
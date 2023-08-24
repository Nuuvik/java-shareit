package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.exceptions.WrongEntityException;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.practicum.shareit.item.CommentMapper.commentToDto;
import static ru.practicum.shareit.item.CommentMapper.mapToNewComment;
import static ru.practicum.shareit.item.ItemMapper.mapToNewItem;


@Service("itemService")
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    private final BookingRepository bookingRepository;

    private final CommentRepository commentRepository;
    private final ItemMapper itemMapper;

    @Transactional(readOnly = true)
    public List<ItemDto> getItemsByUserId(Long userId) {
        List<Item> items = new ArrayList<>(itemRepository.findByOwnerOrderById(userId));
        if (items.isEmpty()) {
            return new ArrayList<>();
        }
        List<ItemDto> itemsDto = items.stream()
                .map(itemMapper::itemToDto)
                .collect(Collectors.toList());
        for (ItemDto itemDto : itemsDto) {
            Long itemDtoId = itemDto.getId();
            if (Objects.equals(userId, itemDto.getOwner())) {
                itemDto.setLastBooking(getLastBookingForItem(itemDtoId));
                itemDto.setNextBooking(getFutureBookingFotItem(itemDtoId));
            }
        }
        return itemsDto;
    }

    @Transactional(readOnly = true)
    public Boolean findUserById(Long userId) {
        return userRepository.existsById(userId);
    }

    @Transactional(readOnly = true)
    public Item getItemById(Long itemId) {
        if (!itemRepository.existsById(itemId)) {
            throw new NotFoundException();
        }
        return itemRepository.findById(itemId).get();
    }

    @Transactional(readOnly = true)
    public ItemDto getItemDtoByItemId(Long itemId, Long userId) {
        Item item = getItemById(itemId);
        ItemDto itemDto = itemMapper.itemToDto(item);
        if (item.getOwner().equals(userId)) {
            itemDto.setLastBooking(getLastBookingForItem(itemId));
            itemDto.setNextBooking(getFutureBookingFotItem(itemId));
        }
        if (commentRepository.findByItemIdOrderByCreatedDesc(itemId) != null) {
            List<Comment> list = (commentRepository.findByItemIdOrderByCreatedDesc(itemId));
            List<CommentDto> commentDtoList = new ArrayList<>();
            for (Comment comment : list) {
                commentDtoList.add(commentToDto(comment));
            }
            itemDto.setComments(commentDtoList);
        }
        return itemDto;
    }

    public Item create(ItemDto itemDto, Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException();
        }
        Item item = mapToNewItem(itemDto, userId);
        itemRepository.save(item);
        return item;
    }

    public Item update(Long id, ItemDto itemDto, Long userId) {
        if ((!userRepository.existsById(userId)) || (!itemRepository.existsById(id)) || (!Objects.equals(itemRepository.findById(id).get().getOwner(), userId))) {
            throw new NotFoundException();
        }
        Item item = itemRepository.findById(id).get();
        return itemRepository.save(new Item(
                id,
                itemDto.getName() != null ? itemDto.getName() : item.getName(),
                itemDto.getDescription() != null ? itemDto.getDescription() : item.getDescription(),
                itemDto.getAvailable() != null ? itemDto.getAvailable() : item.getAvailable(),
                userId,
                itemDto.getRequest() != null ? itemDto.getRequest() : item.getRequest()
        ));
    }

    public void delete(Long id) {
        itemRepository.delete(itemRepository.findById(id).get());
    }

    @Transactional(readOnly = true)
    public List<ItemDto> getItemsByTextSearch(String text) {
        if (text.isBlank()) {
            return new ArrayList<>();
        }
        String query = "%" + text.trim().toLowerCase() + "%";
        return new ArrayList<>(itemRepository.findByNameOrDescription(query)
                .stream()
                .map(itemMapper::itemToDto)
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList)));
    }

    public CommentDto addComment(Long itemId, Long userId, CommentDto commentDto) {
        if (commentDto.getText().equals("")) {
            throw new WrongEntityException();
        }
        if (!itemRepository.existsById(itemId)) {
            throw new NotFoundException();
        }
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException();
        }
        List<Booking> list = bookingRepository.findByBookerIdAndStatusAndEndBeforeOrderByIdDesc(userId, Status.APPROVED, LocalDateTime.now());
        boolean isItemBookingDetected = false;
        for (Booking booking : list) {
            if (booking.getItem().getId().equals(itemId)) {
                isItemBookingDetected = true;
                break;
            }
        }
        if (isItemBookingDetected) {
            Comment comment = mapToNewComment(commentDto, userRepository.findById(userId).get(), itemRepository.findById(itemId).get());
            return commentToDto(commentRepository.save(comment));
        } else throw new WrongEntityException();

    }

    private ItemBookingDto getLastBookingForItem(Long itemId) {
        List<Booking> bookings = bookingRepository.findByItemIdAndStartBeforeAndStatusOrderByEndDesc(itemId,
                LocalDateTime.now(), Status.APPROVED);
        if (bookings.isEmpty()) {
            return null;
        }
        Comparator<Booking> byDateEnd = Comparator.comparing(Booking::getEnd).reversed();
        List<Booking> bookingsSorted = bookings.stream()
                .sorted(byDateEnd)
                .limit(1)
                .collect(Collectors.toList());
        Booking booking = bookingsSorted.get(0);
        return new ItemBookingDto(booking.getId(), booking.getBooker().getId());
    }

    private ItemBookingDto getFutureBookingFotItem(Long itemId) {
        List<Booking> bookings = bookingRepository.findByItemIdAndStartAfterAndStatusOrderByStartAsc(itemId,
                LocalDateTime.now(), Status.APPROVED);
        if (bookings.isEmpty()) {
            return null;
        }
        Comparator<Booking> byDateStart = Comparator.comparing(Booking::getStart);
        List<Booking> bookingsOrdered = bookings.stream()
                .sorted(byDateStart)
                .limit(1)
                .collect(Collectors.toList());
        Booking booking = bookingsOrdered.get(0);
        return new ItemBookingDto(booking.getId(), booking.getBooker().getId());
    }
}
package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.item.ItemDto;
import ru.practicum.shareit.user.UserDto;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class BookingDto {

    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private BookingStatus status;
    private ItemDto item;
    private UserDto booker;
}
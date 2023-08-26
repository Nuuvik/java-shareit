package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.item.Item;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class BookingDto {

    private Long id;

    @NotNull
    @FutureOrPresent
    private LocalDateTime start;

    @NotNull
    @FutureOrPresent
    private LocalDateTime end;

    @NotNull
    private Long itemId;

    private Long bookerId;

    private Item item;

    @Enumerated(EnumType.STRING)
    private Status status;
}
package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.config.Create;
import ru.practicum.shareit.config.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDto {

    private Long id;


    @NotBlank(groups = {Create.class})
    private String name;

    @Size(max = 200, message = "the maximum length of the description is 200 characters", groups = {Create.class, Update.class})
    @NotBlank(groups = {Create.class})
    private String description;

    @NotNull(groups = {Create.class})
    private Boolean available;

    private Long owner;

    private Long request;

    private ItemBookingDto lastBooking;

    private ItemBookingDto nextBooking;

    private List<CommentDto> comments = new ArrayList<>();


}

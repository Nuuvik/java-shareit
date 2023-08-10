package ru.practicum.shareit.item;

import lombok.Data;
import ru.practicum.shareit.config.Create;
import ru.practicum.shareit.config.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
public class ItemDto {

    private Long id;


    @NotBlank(groups = {Create.class})
    private String name;

    @Size(max = 200, message = "максимальная длина описания - 200 символов", groups = {Create.class, Update.class})
    @NotBlank(groups = {Create.class})
    private String description;
    @NotNull(groups = {Create.class})
    private Boolean available;


    public ItemDto(String name, String description, Boolean available) {
        this.name = name;
        this.description = description;
        this.available = available;

    }


}

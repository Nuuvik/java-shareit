package ru.practicum.shareit.item;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Item {

    private Long id;

    private String name;

    private String description;

    private Boolean available;

    private Long owner;


    public Item(Long id, String name, String description, Boolean available, Long owner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
        this.owner = owner;
    }
}

package ru.practicum.shareit.item;

import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    public static ItemDto itemToDto(Item item) {
        return new ItemDto(
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getRequest()
        );
    }

    public static Item dtoToItem(Long id, ItemDto itemDto, Long userId) {
        return new Item(
                id,
                itemDto.getName(),
                itemDto.getDescription(),
                itemDto.getAvailable(),
                userId,
                itemDto.getRequest()
        );
    }
}

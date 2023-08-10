package ru.practicum.shareit.item;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class ItemMapper {

    public ItemDto itemToDto(Item item) {
        return new ItemDto(
                item.getName(),
                item.getDescription(),
                item.getAvailable()
        );
    }

    public Item dtoToItem(Long id, ItemDto itemDto, Long userId) {
        return new Item(
                id,
                itemDto.getName(),
                itemDto.getDescription(),
                itemDto.getAvailable(),
                userId
        );
    }

    public List<ItemDto> itemListToDto(List<Item> items) {
        return items.stream()
                .map(ItemMapper::itemToDto)
                .collect(Collectors.toList());
    }
}

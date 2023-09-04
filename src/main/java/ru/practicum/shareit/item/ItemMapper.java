package ru.practicum.shareit.item;


import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ItemMapper {

    Item toItem(ItemDto itemDto);

    ItemDto toDto(Item item);
}
package ru.practicum.shareit.item;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ItemMapper {

    ItemMapper itemMapper = Mappers.getMapper(ItemMapper.class);

    Item toItem(ItemDto itemDto);

    ItemDto toDto(Item item);
}
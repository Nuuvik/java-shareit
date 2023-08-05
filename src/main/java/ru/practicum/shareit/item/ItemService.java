package ru.practicum.shareit.item;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static ru.practicum.shareit.item.ItemMapper.dtoToItem;

@Service("itemService")
@Data
public class ItemService {

    ItemStorage itemStorage;
    UserService userService;


    @Autowired
    public ItemService(@Qualifier("inMemoryItemStorage") ItemStorage itemStorage, UserService userService) {
        this.itemStorage = itemStorage;
        this.userService = userService;
    }


    public List<Item> getItemsByUserId(Long userId) {
        return itemStorage.getItemsByUserId(userId);
    }

    public Item getItemById(Long itemId) {

        return itemStorage.getItem(itemId);
    }

    public Item create(ItemDto itemDto, Long userId) {
        if (!userService.getUsersId().contains(userId)) {
            throw new NotFoundException();
        }
        Item item = dtoToItem(null, itemDto, userId);
        itemStorage.save(item);
        return item;
    }

    public Item update(Long id, ItemDto itemDto, Long userId) {
        if (!Objects.equals(itemStorage.getItem(id).getOwner(), userId)) {
            throw new NotFoundException();
        }
        Item item = dtoToItem(id, itemDto, userId);
        itemStorage.update(id, item, userId);
        return itemStorage.getItem(id);
    }

    public void delete(Long id) {
        itemStorage.delete(id);
    }

    public List<Item> getItemsByTextSearch(String text) {
        List<Item> list = new ArrayList<>();
        if (text.isBlank()) {
            return list;
        }
        for (Item item : itemStorage.getItems()) {
            if ((item.getAvailable()) && ((item.getDescription().toLowerCase().contains(text.toLowerCase())) || (item.getName().toLowerCase().contains(text.toLowerCase())))) {
                list.add(item);
            }
        }
        return list;
    }
}

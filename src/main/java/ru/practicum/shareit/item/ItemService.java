package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.exceptions.UnauthorizedException;
import ru.practicum.shareit.user.UserStorage;

import java.util.List;



@Service("itemService")
public class ItemService {

    private final ItemStorage itemStorage;
    private final UserStorage userStorage;


    @Autowired
    public ItemService(ItemStorage itemStorage, UserStorage userStorage) {
        this.itemStorage = itemStorage;
        this.userStorage = userStorage;
    }


    public List<Item> getItemsByUserId(Long userId) {
        return itemStorage.getItemsByUserId(userId);
    }

    public Item getItemById(Long itemId) {
        return itemStorage.getItem(itemId)
                .orElseThrow(NotFoundException::new);
    }

    public Item create(ItemDto itemDto, Long userId) {
        if (!userStorage.getUsersId().contains(userId)) {
            throw new NotFoundException();
        }
        Item item = ItemMapper.dtoToItem(null, itemDto, userId);
        itemStorage.save(item);
        return item;
    }

    public Item update(Long id, ItemDto itemDto, Long userId) {
        Item item = ItemMapper.dtoToItem(id, itemDto, userId);

        Item existingItem = itemStorage.getItem(id).orElseThrow(NotFoundException::new);

        if (!existingItem.getOwner().equals(userId)) {
            throw new UnauthorizedException("Вы не имеете права обновлять эту вещь");
        }
        itemStorage.update(id, item, userId);
        return itemStorage.getItem(id)
                .orElseThrow(NotFoundException::new);
    }

    public void delete(Long id) {
        itemStorage.delete(id);
    }

    public List<Item> getItemsByTextSearch(String text) {
        return itemStorage.getItemsByTextSearch(text);
    }
}

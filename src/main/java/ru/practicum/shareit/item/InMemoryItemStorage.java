package ru.practicum.shareit.item;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exceptions.NotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Component("inMemoryItemStorage")
public class InMemoryItemStorage implements ItemStorage {

    private Map<Long, Item> itemRepository = new HashMap<>();

    private long generatorId = 0;

    @Override
    public void save(Item item) {
        item.setId(generateId());
        itemRepository.put(item.getId(), item);
    }

    @Override
    public void update(Long id, Item updatedItem, Long userId) {
        Item item = itemRepository.get(id);
        if (item == null) {
            throw new NotFoundException("Вещь не найдена");
        }

        if (updatedItem.getName() != null && !updatedItem.getName().isBlank()) {
            item.setName(updatedItem.getName());
        }
        if (updatedItem.getAvailable() != null) {
            item.setAvailable(updatedItem.getAvailable());
        }
        if (updatedItem.getDescription() != null && !updatedItem.getDescription().isBlank()) {
            item.setDescription(updatedItem.getDescription());
        }
    }

    @Override
    public void delete(Long id) {
        Item item = itemRepository.remove(id);
        if (item == null) {
            throw new NotFoundException("Вещь не найдена");
        }
    }


    @Override
    public Optional<Item> getItem(Long id) {
        return Optional.ofNullable(itemRepository.get(id));
    }

    @Override
    public List<Item> getItemsByUserId(Long userId) {
        List<Item> list = new ArrayList<>();
        for (Item item : itemRepository.values()) {
            if (item.getOwner().equals(userId)) {
                list.add(item);
            }
        }
        return list;
    }

    @Override
    public List<Item> getItemsByTextSearch(String text) {
        String searchText = text.toLowerCase();
        if (text.isBlank()) {
            return List.of();
        }

        return itemRepository.values().stream()
                .filter(item -> item.getAvailable() &&
                        (item.getDescription().toLowerCase().contains(searchText) ||
                                item.getName().toLowerCase().contains(searchText)))
                .collect(Collectors.toList());
    }

    private long generateId() {
        return ++generatorId;
    }

}

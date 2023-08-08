package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.config.Create;
import ru.practicum.shareit.config.Update;

import java.util.List;


@RestController
@RequestMapping("/items")
@Slf4j
@Validated
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<Item> getItems(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("получен запрос GET /items");
        return itemService.getItemsByUserId(userId);
    }

    @GetMapping("/{itemId}")
    public Item getItemById(@PathVariable Long itemId) {
        log.info("получен запрос GET /items/id");
        return itemService.getItemById(itemId);
    }

    @GetMapping("/search")
    public List<Item> getItemByTextSearch(@RequestParam String text) {
        log.info("получен запрос GET /items/search");
        return itemService.getItemsByTextSearch(text);
    }

    @PostMapping()
    public Item create(@RequestBody @Validated(Create.class) ItemDto itemDto, @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("получен запрос POST /items");
        return itemService.create(itemDto, userId);
    }

    @PatchMapping("/{itemId}")
    public Item update(@PathVariable Long itemId, @RequestBody @Validated(Update.class) ItemDto itemDto, @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.debug("получен запрос PATCH /items");
        return itemService.update(itemId, itemDto, userId);
    }
}
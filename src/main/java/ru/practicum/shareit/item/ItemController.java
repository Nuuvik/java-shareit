package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.config.Create;
import ru.practicum.shareit.config.Update;

import java.util.List;


@RestController
@RequestMapping("/items")
@Slf4j
@Validated
public class ItemController {
    private static final String USER_ID_HEADER = "X-Sharer-User-Id";
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<ItemDto> getItems(@RequestHeader(USER_ID_HEADER) Long userId) {
        log.info("request received GET /items");
        return itemService.getItemsByUserId(userId);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItemById(@PathVariable Long itemId, @RequestHeader(USER_ID_HEADER) Long userId) {
        log.info("request received GET /items/id");
        return itemService.getItemById(itemId, userId);
    }

    @GetMapping("/search")
    public List<ItemDto> getItemByTextSearch(@RequestParam String text) {
        log.info("request received GET /items/search");
        return itemService.getItemsByTextSearch(text);
    }

    @PostMapping
    public Item create(@RequestBody @Validated(Create.class) ItemDto itemDto, @RequestHeader(USER_ID_HEADER) Long userId) {
        log.info("request received POST /items");
        return itemService.create(itemDto, userId);
    }

    @PatchMapping("/{itemId}")
    public Item update(@PathVariable Long itemId, @RequestBody @Validated(Update.class) ItemDto itemDto, @RequestHeader(USER_ID_HEADER) Long userId) {
        log.debug("request received PATCH /items");
        return itemService.update(itemId, itemDto, userId);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto addComment(@PathVariable Long itemId, @RequestHeader(USER_ID_HEADER) Long userId, @RequestBody CommentDto commentDto) {
        log.info("request received POST /items/id/comment");
        return itemService.addComment(itemId, userId, commentDto);
    }
}
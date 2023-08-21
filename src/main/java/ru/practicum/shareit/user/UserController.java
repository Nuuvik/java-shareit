package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.config.Create;
import ru.practicum.shareit.config.Update;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/users")
@Slf4j
@Validated
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public List<UserDto> getUsers() {
        log.info("получен запрос GET /users");
        List<User> users = userService.getUsers();
        return UserMapper.userListToDto(users);
    }

    @GetMapping("/{userId}")
    public Optional<User> getUsersById(@PathVariable Long userId) {
        log.info("получен запрос GET /users/id");
        return userService.getUsersById(userId);

    }


    @PostMapping()
    public UserDto create(@RequestBody @Validated(Create.class) UserDto userDto) {
        log.info("получен запрос POST /users");
        return userService.create(userDto);
    }

    @PatchMapping("/{userId}")
    public UserDto update(@PathVariable Long userId, @RequestBody @Validated(Update.class) UserDto userDto) {
        log.debug("получен запрос PATCH /users");
        User user = userService.updateUser(userId, userDto);
        return UserMapper.userToDto(user);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable Long userId) {
        log.debug("получен запрос DELETE /users");
        userService.delete(userId);
    }
}

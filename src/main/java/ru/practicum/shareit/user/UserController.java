package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
        log.info("request received GET /users");
        List<User> users = userService.getUsers();
        return UserMapper.userListToDto(users);
    }

    @GetMapping("/{userId}")
    public Optional<User> getUsersById(@PathVariable Long userId) {
        log.info("request received GET /users/id");
        return userService.getUsersById(userId);

    }


    @PostMapping()
    public UserDto create(@RequestBody @Validated(Create.class) UserDto userDto) {
        log.info("request received POST /users");
        return userService.create(userDto);
    }

    @PatchMapping("/{userId}")
    public UserDto update(@PathVariable Long userId, @RequestBody @Validated(Update.class) UserDto userDto) {
        log.debug("request received PATCH /users");
        User user = userService.updateUser(userId, userDto);
        return UserMapper.userToDto(user);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable Long userId) {
        log.debug("request received DELETE /users");
        userService.delete(userId);
    }
}

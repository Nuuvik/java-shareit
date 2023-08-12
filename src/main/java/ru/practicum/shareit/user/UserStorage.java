package ru.practicum.shareit.user;

import java.util.List;
import java.util.Optional;

public interface UserStorage {
    void save(User user);

    void update(Long id, User user);

    void delete(Long id);

    Optional<User> getUser(Long id);

    List<User> getUsers();

    List<Long> getUsersId();
}

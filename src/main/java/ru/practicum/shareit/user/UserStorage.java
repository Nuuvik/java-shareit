package ru.practicum.shareit.user;

import java.util.List;

public interface UserStorage {
    void save(User user);

    void update(Long id, User user);

    void delete(Long id);

    User getUser(Long id);

    List<User> getUsers();

    List<Long> getUsersId();
}

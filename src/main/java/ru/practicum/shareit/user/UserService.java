package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;

import java.util.List;


@Service("userService")
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }


    public List<User> getUsers() {
        return userStorage.getUsers();
    }

    public List<Long> getUsersId() {
        return userStorage.getUsersId();
    }

    public User getUsersById(Long userId) {
        return userStorage.getUser(userId)
                .orElseThrow(NotFoundException::new);
    }

    public User create(UserDto userDto) {
        User user = UserMapper.dtoToUser(null, userDto);
        userStorage.save(user);
        return user;
    }

    public User update(Long id, UserDto userDto) {
        User user = UserMapper.dtoToUser(id, userDto);
        userStorage.update(id, user);
        return userStorage.getUser(id)
                .orElseThrow(NotFoundException::new);
    }

    public void delete(Long id) {
        userStorage.delete(id);
    }
}

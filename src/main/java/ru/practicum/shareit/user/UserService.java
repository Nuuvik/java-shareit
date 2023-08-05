package ru.practicum.shareit.user;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

import static ru.practicum.shareit.user.UserMapper.dtoToUser;

@Service("userService")
@Data
public class UserService {

    UserStorage userStorage;

    @Autowired
    public UserService(@Qualifier("inMemoryUserStorage") UserStorage userStorage) {
        this.userStorage = userStorage;
    }


    public List<User> getUsers() {
        return userStorage.getUsers();
    }

    public List<Long> getUsersId() {
        return userStorage.getUsersId();
    }

    public User getUsersById(Long userId) {
        return userStorage.getUser(userId);
    }

    public User create(UserDto userDto) {
        User user = dtoToUser(null, userDto);
        userStorage.save(user);
        return user;
    }

    public User update(Long id, UserDto userDto) {
        User user = dtoToUser(id, userDto);
        userStorage.update(id, user);
        return userStorage.getUser(id);
    }

    public void delete(Long id) {
        userStorage.delete(id);
    }
}

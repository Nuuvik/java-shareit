package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;


@Service("userService")
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Boolean isExistUserById(Long userId) {
        return userRepository.existsById(userId);
    }

    @Transactional(readOnly = true)
    public UserDto getUserDtoByUserId(Long userId) {
        User user = getUsersById(userId).get();
        return UserMapper.userToDto(user);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUsersById(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException();
        }
        return userRepository.findById(userId);
    }

    @Transactional
    public UserDto create(UserDto userDto) {
        User user = UserMapper.dtoToUser(userDto);

        return UserMapper.userToDto(userRepository.save(user));
    }

    public User updateUser(Long id, UserDto userDto) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException();
        }
        User user = userRepository.findById(id).get();
        return userRepository.save(new User(
                id,
                userDto.getName() != null ? userDto.getName() : user.getName(),
                userDto.getEmail() != null ? userDto.getEmail() : user.getEmail()
        ));
    }

    public void delete(Long userId) {
        userRepository.delete(userRepository.findById(userId).get());
    }
}

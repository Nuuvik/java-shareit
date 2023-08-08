package ru.practicum.shareit.user;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class UserMapper {
    public UserDto userToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public User dtoToUser(Long id, UserDto userDto) {
        return new User(
                id,
                userDto.getName(),
                userDto.getEmail()
        );
    }

    public List<UserDto> userListToDto(List<User> users) {
        return users.stream()
                .map(UserMapper::userToDto)
                .collect(Collectors.toList());
    }


}

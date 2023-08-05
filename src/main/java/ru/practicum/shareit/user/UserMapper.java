package ru.practicum.shareit.user;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public static UserDto userToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public static User dtoToUser(Long id, UserDto userDto) {
        return new User(
                id,
                userDto.getName(),
                userDto.getEmail()
        );
    }


}

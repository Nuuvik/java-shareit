package ru.practicum.shareit.exceptions;

public class EmailAlreadyExistsException extends IllegalArgumentException {

    public EmailAlreadyExistsException() {
        super("User with such an email already exists");
    }
}

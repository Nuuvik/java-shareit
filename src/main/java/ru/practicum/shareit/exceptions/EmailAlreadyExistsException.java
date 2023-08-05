package ru.practicum.shareit.exceptions;

public class EmailAlreadyExistsException extends IllegalArgumentException {

    public EmailAlreadyExistsException() {
        super("Пользователь с таким email уже есть");
    }
}

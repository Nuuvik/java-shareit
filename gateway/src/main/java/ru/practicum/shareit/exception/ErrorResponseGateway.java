package ru.practicum.shareit.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
class ErrorResponseGateway {
    @Getter
    String error;
}
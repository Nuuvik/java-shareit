package ru.practicum.shareit.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.exceptions.UnauthorizedException;

import java.util.Map;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleBadRequest(final RuntimeException e) {
        return Map.of("error", e.getMessage());
    }
}

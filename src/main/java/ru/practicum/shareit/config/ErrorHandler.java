package ru.practicum.shareit.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.exceptions.UnauthorizedException;
import ru.practicum.shareit.exceptions.WrongEntityException;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler({UnauthorizedException.class, NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleBadRequest(final RuntimeException e) {
        log.debug("received HTTP-status 404 Not found {}", e.getMessage(), e);
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, WrongEntityException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleBadRequest(Exception e) {
        log.debug("received HTTP-status 400 Bad request {}", e.getMessage(), e);
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleInternalServerError(Throwable e) {
        log.debug("received HTTP-status 500 Internal server error {}", e.getMessage(), e);
        return Map.of("error", e.getMessage());
    }
}

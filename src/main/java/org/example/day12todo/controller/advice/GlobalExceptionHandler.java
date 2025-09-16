package org.example.day12todo.controller.advice;

import org.example.day12todo.exception.InvalidTextTodoException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidTextTodoException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseException exceptionHandler(Exception e) {
        return new ResponseException(e.getMessage());
    }
}

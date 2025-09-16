package org.example.day12todo.exception;

public class InvalidTextTodoException extends RuntimeException {
    public InvalidTextTodoException(String message) {
        super(message);
    }
}

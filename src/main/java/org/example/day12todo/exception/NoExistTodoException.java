package org.example.day12todo.exception;

public class NoExistTodoException extends RuntimeException {
    public NoExistTodoException(String message) {
        super(message);
    }
}

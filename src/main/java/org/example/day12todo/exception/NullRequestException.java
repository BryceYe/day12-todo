package org.example.day12todo.exception;

public class NullRequestException extends RuntimeException {
    public NullRequestException(String message) {
        super(message);
    }
}

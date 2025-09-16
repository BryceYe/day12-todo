package org.example.day12todo.service;

import org.example.day12todo.dto.TodoRequest;
import org.example.day12todo.entity.Todo;

import java.util.List;

public interface TodoService {
    List<Todo> index();

    Todo createTodo(TodoRequest request);

    Todo updateTodo(String id, TodoRequest request);

    void deleteTodo(String id);
}

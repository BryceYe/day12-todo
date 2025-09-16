package org.example.day12todo.dto.mapper;

import org.example.day12todo.dto.TodoRequest;
import org.example.day12todo.dto.TodoResponse;
import org.example.day12todo.entity.Todo;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class TodoMapper {
    public static TodoResponse toResponse(Todo todo) {
        TodoResponse todoResponse = new TodoResponse();
        BeanUtils.copyProperties(todo, todoResponse);
        return todoResponse;
    }

    public static List<TodoResponse> toResponse(List<Todo> todos) {
        return todos.stream().map(TodoMapper::toResponse).toList();
    }

    public static TodoRequest toRequest(Todo todo) {
        TodoRequest todoRequest = new TodoRequest();
        BeanUtils.copyProperties(todo, todoRequest);
        return todoRequest;
    }
}

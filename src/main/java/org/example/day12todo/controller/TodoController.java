package org.example.day12todo.controller;

import lombok.RequiredArgsConstructor;
import org.example.day12todo.dto.TodoResponse;
import org.example.day12todo.dto.mapper.TodoMapper;
import org.example.day12todo.entity.Todo;
import org.example.day12todo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/todos")
public class TodoController {
    private final TodoService todoService;

    @GetMapping
    public List<TodoResponse> index() {
        return TodoMapper.toResponse(todoService.index());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TodoResponse createTodo(@RequestBody Todo todo) {
        return TodoMapper.toResponse(todoService.createTodo(todo));
    }
}

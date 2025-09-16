package org.example.day12todo.controller;

import lombok.RequiredArgsConstructor;
import org.example.day12todo.entity.Todo;
import org.example.day12todo.service.TodoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todos")
public class TodoController {
    private final TodoService todoService;

    @GetMapping
    List<Todo> index() {
        return todoService.findAll();
    }
}

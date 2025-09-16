package org.example.day12todo.controller;

import org.example.day12todo.entity.Todo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {
    @GetMapping
    List<Todo> index() {
        return List.of();
    }
}

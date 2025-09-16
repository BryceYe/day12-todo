package org.example.day12todo.service;

import org.example.day12todo.entity.Todo;

import java.util.List;

public interface TodoService {
    List<Todo> findAll();
}

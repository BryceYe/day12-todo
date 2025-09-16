package org.example.day12todo.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.day12todo.dto.TodoRequest;
import org.example.day12todo.entity.Todo;
import org.example.day12todo.exception.InvalidTextTodoException;
import org.example.day12todo.repository.TodoRepository;
import org.example.day12todo.service.TodoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;

    public List<Todo> index(){
        return todoRepository.findAll();
    }

    @Override
    public Todo createTodo(TodoRequest request) {
        if (request.getText() == null || request.getText().isEmpty()) {
            throw new InvalidTextTodoException("Todo text cannot be empty or null");
        }
        Todo todo = new Todo();
        BeanUtils.copyProperties(request, todo);
        return todoRepository.save(todo);
    }

}

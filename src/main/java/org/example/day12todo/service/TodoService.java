package org.example.day12todo.service;

import lombok.RequiredArgsConstructor;
import org.example.day12todo.dto.TodoRequest;
import org.example.day12todo.entity.Todo;
import org.example.day12todo.exception.InvalidTextTodoException;
import org.example.day12todo.exception.NoExistTodoException;
import org.example.day12todo.exception.NullRequestException;
import org.example.day12todo.repository.TodoRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;


    @Tool(name = "findAllTodo", description = "Find all todo")
    public List<Todo> index() {
        return todoRepository.findAll();

    }

    @Tool(name = "createTodo", description = "Create todo")
    public Todo createTodo(TodoRequest request) {
        if (request.getText() == null || request.getText().isEmpty()) {
            throw new InvalidTextTodoException("Todo text cannot be empty or null");
        }
        Todo todo = new Todo();
        BeanUtils.copyProperties(request, todo);
        return todoRepository.save(todo);
    }

    public Todo updateTodo(String id, TodoRequest request) {
        if (request == null) {
            throw new NullRequestException("Request body cannot be null");
        }
        Optional<Todo> todo = todoRepository.findById(id);
        if (todo.isEmpty()) {
            throw new NoExistTodoException("Todo not found with id: " + id);
        }
        if (request.getText() == null) {
            throw new InvalidTextTodoException("Todo text cannot be null");
        }
        if (request.getText().isEmpty()) {
            throw new InvalidTextTodoException("Todo text cannot be empty");
        }
        Todo updatedTodo = new Todo();
        BeanUtils.copyProperties(request, updatedTodo);
        updatedTodo.setId(id);
        return todoRepository.save(updatedTodo);
    }

    public void deleteTodo(String id) {
        Optional<Todo> todo = todoRepository.findById(id);
        if (todo.isEmpty()) {
            throw new NoExistTodoException("Todo not found with id: " + id);
        }
        todoRepository.deleteById(id);
    }

}

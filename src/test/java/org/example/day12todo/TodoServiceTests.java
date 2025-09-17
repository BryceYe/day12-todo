package org.example.day12todo;

import org.example.day12todo.repository.TodoRepository;
import org.example.day12todo.service.TodoService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class TodoServiceTests {
    @InjectMocks
    private TodoService todoServiceImpl;

    @Mock
    private TodoRepository todoRepository;

}

package org.example.day12todo;

import org.example.day12todo.entity.Todo;
import org.example.day12todo.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class Day12TodoApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoRepository todoRepository;

    @BeforeEach
    void cleanUp() {
        todoRepository.deleteAll();
    }

    @Test
    void should_response_empty_list_when_index_no_any_todo() throws Exception {
        MockHttpServletRequestBuilder request = get("/todos")
            .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void should_response_one_todo_when_index_one_todo() throws Exception {
        Todo todo = new Todo(null, "Buy milk", false);
        todoRepository.save(todo);

        MockHttpServletRequestBuilder request = get("/todos")
            .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$[0].id").exists())
            .andExpect(jsonPath("$[0].text").value("Buy milk"))
            .andExpect(jsonPath("$[0].done").value(false));
    }

    @Test
    void should_response_one_todo_when_add_one_todo() throws Exception {
        String requestBody = """
            {
                "text": "Buy milk",
                "done": false
            }
            """;
        MockHttpServletRequestBuilder request = post("/todos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody);

        mockMvc.perform(request)
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.text").value("Buy milk"))
            .andExpect(jsonPath("$.done").value(false));
    }

    @Test
    void should_response_422_when_add_todo_without_text() throws Exception {
        String requestBody = """
            {
                "text": "",
                "done": false
            }
            """;
        MockHttpServletRequestBuilder request = post("/todos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody);

        mockMvc.perform(request)
            .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void should_response_422_when_add_todo_with_null_text() throws Exception {
        String requestBody = """
            {
                "done": false
            }
            """;
        MockHttpServletRequestBuilder request = post("/todos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody);

        mockMvc.perform(request)
            .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void should_ignore_client_sent_id_when_add_todo_with_id() throws Exception {
        String requestBody = """
            {
                "id": "client-sent",
                "text": "Buy milk",
                "done": false
            }
            """;
        MockHttpServletRequestBuilder request = post("/todos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody);

        mockMvc.perform(request)
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.id").value(not("client-sent")))
            .andExpect(jsonPath("$.text").value("Buy milk"))
            .andExpect(jsonPath("$.done").value(false));
    }

    @Test
    void should_response_todo_when_update_todo() throws Exception {
        Todo todo = new Todo(null, "Buy milk", false);
        Todo savedTodo = todoRepository.save(todo);

        String requestBody = """
            {
                "text": "Buy snacks",
                "done": true
            }
            """;
        MockHttpServletRequestBuilder request = put("/todos/" + savedTodo.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody);

        mockMvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(savedTodo.getId()))
            .andExpect(jsonPath("$.text").value("Buy snacks"))
            .andExpect(jsonPath("$.done").value(true));
    }

    @Test
    void should_response_id_123_todo_when_update_todo_with_put_id_123_and_body_id_456() throws Exception {
        Todo todo = new Todo("123", "Buy milk", true);
        todoRepository.save(todo);

        Todo todo2 = new Todo("456", "Buy milk", true);
        todoRepository.save(todo2);

        String requestBody = """
            {
                "id": "456",
                "text": "Buy snacks",
                "done": true
            }
            """;
        MockHttpServletRequestBuilder request = put("/todos/123")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody);

        mockMvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value("123"))
            .andExpect(jsonPath("$.text").value("Buy snacks"))
            .andExpect(jsonPath("$.done").value(true));
    }

    @Test
    void should_response_404_when_update_non_exist_todo() throws Exception {
        String requestBody = """
            {
                "text": "Buy snacks",
                "done": true
            }
            """;
        MockHttpServletRequestBuilder request = put("/todos/999")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody);

        mockMvc.perform(request)
            .andExpect(status().isNotFound());
    }

}

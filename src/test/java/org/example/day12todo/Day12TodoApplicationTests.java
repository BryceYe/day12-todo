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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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


}

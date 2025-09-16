package org.example.day12todo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_allow_cross_origin_requests() throws Exception {
        MockHttpServletRequestBuilder request = options("/todos")
            .header("Origin", "http://localhost:3000")
            .header("Access-Control-Request-Method", "GET", "POST", "PUT", "DELETE");

        mockMvc.perform(request)
            .andExpect(status().isOk());
    }
}

package com.college.library;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Map<String, Object> payload;

    @BeforeEach
    void setup() {
        String uniqueIsbn = "978161729" + System.nanoTime();
        payload = new HashMap<>();
        payload.put("title", "Spring in Action");
        payload.put("author", "Craig Walls");
        payload.put("isbn", uniqueIsbn);
        payload.put("category", "Programming");
        payload.put("publisher", "Manning");
        payload.put("edition", "6th");
        payload.put("shelfNumber", "B1-R1");
        payload.put("totalCopies", 2);
        payload.put("availableCopies", 2);
    }

    @Test
    void shouldCreateBook() throws Exception {
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Spring in Action"))
                .andExpect(jsonPath("$.status").value("AVAILABLE"));
    }

    @Test
    void shouldSearchBook() throws Exception {
        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload))).andExpect(status().isCreated());

        mockMvc.perform(get("/api/books/search").param("query", "Craig"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].author").value("Craig Walls"));
    }

    @Test
    void shouldIssueAndReturnBook() throws Exception {
        String response = mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(put("/api/books/{id}/issue", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.availableCopies").value(1));

        mockMvc.perform(get("/api/books/{id}/availability", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("AVAILABLE"));

        mockMvc.perform(put("/api/books/{id}/return", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.availableCopies").value(2));
    }
}

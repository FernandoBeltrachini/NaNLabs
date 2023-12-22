package com.fernet.spacex.controller;

import com.fernet.spacex.service.CardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class CardControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CardService cardService;
    @Test
    public void testCreateCardEndpoint() throws Exception {
        String requestBody = "{\"type\": \"issue\", \"description\": \"description\", \"tittle\": \"tittle\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}


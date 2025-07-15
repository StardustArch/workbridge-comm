package com.workbrigde.chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workbrigde.chat.dto.MessageDTO;
import com.workbrigde.chat.model.ChatMessage;
import com.workbrigde.chat.repository.MessageRepository;
import com.workbrigde.chat.service.KafkaMessageProducer;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MessageController.class)
public class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KafkaMessageProducer kafkaProducer;

    @MockBean
    private MessageRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldSendMessage() throws Exception {
        MessageDTO dto = new MessageDTO("user1", "user2", "Olá!");

        mockMvc.perform(post("/api/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isAccepted());

        verify(kafkaProducer, times(1)).sendMessage(any(ChatMessage.class));
    }

    @Test
    void shouldReturnMessagesBetweenUsers() throws Exception {
        ChatMessage msg = new ChatMessage(null, "user1", "user2", "Olá!", LocalDateTime.now());
        when(repository.findBySenderIdAndReceiverId("user1", "user2"))
                .thenReturn(List.of(msg));

        mockMvc.perform(get("/api/messages")
                .param("senderId", "user1")
                .param("receiverId", "user2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].senderId").value("user1"))
                .andExpect(jsonPath("$[0].receiverId").value("user2"))
                .andExpect(jsonPath("$[0].content").value("Olá!"));
    }
}

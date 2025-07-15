package com.workbrigde.chat.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.workbrigde.chat.controller.WebSocketMessageController;
import com.workbrigde.chat.model.ChatMessage;
import com.workbrigde.chat.repository.MessageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaMessageConsumer {

    private final MessageRepository repository;
    private final WebSocketMessageController webSocketMessageController;

    @KafkaListener(topics = "chat-topic", groupId = "chat-group")
    public void listen(ChatMessage message) {
        System.out.println("Mensagem recebida: " + message);

        repository.save(message);
        webSocketMessageController.sendToWebSocket(message);

    }
}

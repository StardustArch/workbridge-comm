package com.workbrigde.chat.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.workbrigde.chat.model.ChatMessage;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaMessageConsumer {

    private final MessageService messageService;

    @KafkaListener(topics = "chat-topic", groupId = "chat-group")
    public void listen(ChatMessage message) {
        System.out.println("Mensagem recebida: " + message);
        messageService.processAndSave(message);
    }
}

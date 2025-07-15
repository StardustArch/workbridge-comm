package com.workbrigde.chat.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.workbrigde.chat.model.ChatMessage;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaMessageProducer {

    private final KafkaTemplate<String, ChatMessage> kafkaTemplate;

    public void sendMessage(ChatMessage message) {
        System.out.println("Enviando mensagem para Kafka: " + message);
        kafkaTemplate.send("chat-topic", message);
    }
}

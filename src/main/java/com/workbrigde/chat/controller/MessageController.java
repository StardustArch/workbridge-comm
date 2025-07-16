package com.workbrigde.chat.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.workbrigde.chat.dto.MessageDTO;
import com.workbrigde.chat.mapper.ChatMessageMapper;
import com.workbrigde.chat.model.ChatMessage;
import com.workbrigde.chat.service.KafkaMessageProducer;
import com.workbrigde.chat.service.MessageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class MessageController {

    private final KafkaMessageProducer kafkaProducer;
    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<Void> sendMessage(@RequestBody MessageDTO message) {
        ChatMessage chatMessage = ChatMessageMapper.toEntity(message);
        kafkaProducer.sendMessage(chatMessage);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public List<ChatMessage> getMessages(
            @RequestParam String senderId,
            @RequestParam String receiverId) {
        return messageService.getMessages(senderId, receiverId);
    }
}

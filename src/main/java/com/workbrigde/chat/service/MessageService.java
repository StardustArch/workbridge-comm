package com.workbrigde.chat.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.workbrigde.chat.model.ChatMessage;
import com.workbrigde.chat.repository.MessageRepository;
import com.workbrigde.chat.controller.WebSocketMessageController;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository repository;
    private final WebSocketMessageController webSocketMessageController;

    public void processAndSave(ChatMessage message) {
        repository.save(message);
        webSocketMessageController.sendToWebSocket(message);
    }

    public List<ChatMessage> getMessages(String senderId, String receiverId) {
        return repository.findBySenderIdAndReceiverId(senderId, receiverId);
    }
}

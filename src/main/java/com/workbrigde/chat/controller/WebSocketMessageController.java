package com.workbrigde.chat.controller;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.workbrigde.chat.model.ChatMessage;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class WebSocketMessageController {

    private final SimpMessagingTemplate messagingTemplate;

    public void sendToWebSocket(ChatMessage message) {
        String topic = String.format("/topic/messages/%s", message.getReceiverId());
        messagingTemplate.convertAndSend(topic, message);
    }
}

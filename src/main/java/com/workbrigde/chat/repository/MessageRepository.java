package com.workbrigde.chat.repository;

import com.workbrigde.chat.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findBySenderIdAndReceiverId(String senderId, String receiverId);
}

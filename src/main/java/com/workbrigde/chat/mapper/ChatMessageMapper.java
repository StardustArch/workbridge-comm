package com.workbrigde.chat.mapper;

import com.workbrigde.chat.dto.MessageDTO;
import com.workbrigde.chat.model.ChatMessage;

public class ChatMessageMapper {

    public static ChatMessage toEntity(MessageDTO dto) {
        if (dto == null) {
            return null;
        }
        ChatMessage entity = new ChatMessage();
        entity.setSenderId(dto.getSenderId());
        entity.setReceiverId(dto.getReceiverId());
        entity.setContent(dto.getContent());
        // timestamp e id ficam a cargo da entidade, não setamos aqui
        return entity;
    }

    public static MessageDTO toDto(ChatMessage entity) {
        if (entity == null) {
            return null;
        }
        MessageDTO dto = new MessageDTO();
        dto.setSenderId(entity.getSenderId());
        dto.setReceiverId(entity.getReceiverId());
        dto.setContent(entity.getContent());
        return dto;
    }
}


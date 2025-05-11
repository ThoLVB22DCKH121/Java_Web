package com.example.springboot.converter;

import com.example.springboot.models.ChatMessageDTO;
import com.example.springboot.repository.entity.Message;
import com.example.springboot.service.ConversationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ChatMessageDTOConverter {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private ConversationService conversationService;

    public ChatMessageDTO toChatMessageDTO(Message message) {
        ChatMessageDTO chatMessageDTO = modelMapper.map(message, ChatMessageDTO.class);
        return chatMessageDTO;
    }

    public Message toMessage(ChatMessageDTO chatMessageDTO) {
        Message message = modelMapper.map(chatMessageDTO, Message.class);
        return message;
    }

    public List<ChatMessageDTO> toChatMessageDTOList(List<Message> messages) {
        return messages.stream()
                .map(this::toChatMessageDTO)
                .collect(Collectors.toList());
    }
}

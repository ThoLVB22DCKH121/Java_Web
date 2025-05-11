package com.example.springboot.service;

import com.example.springboot.repository.entity.Conversation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ConversationService {
    public Conversation createConversation(Long userId1, Long userId2, Long propertyId);
    public Conversation getConversationById(Long id);
    public Conversation getConversationByUserId1AndUserId2AndPropertyId(Long userId1, Long userId2, Long propertyId);
    public Page<Conversation> getConversationsByUserId(Long userId, Pageable pageable);
}

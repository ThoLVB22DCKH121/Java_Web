package com.example.springboot.service.chat;

import com.example.springboot.repository.ConversationRepository;
import com.example.springboot.repository.entity.Conversation;
import com.example.springboot.repository.entity.User;
import com.example.springboot.service.ConversationService;
import com.example.springboot.service.PropertyService;
import com.example.springboot.service.auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ConversationServiceImpl implements ConversationService {
    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private PropertyService propertyService;

    @Override
    public Conversation createConversation(Long userId1, Long userId2, Long propertyId) {
        Conversation conversation = new Conversation();
        conversation.setCreateAt(LocalDateTime.now());
        List<User> users = new ArrayList<>();
        users.add(userService.getUserById(userId1));
        users.add(userService.getUserById(userId2));
        conversation.setUsers(users);
        conversation.setProperty(propertyService.getPropertyById(propertyId));
        return conversationRepository.save(conversation);
    }

    @Override
    public Conversation getConversationById(Long id) {
        return conversationRepository.findById(id).orElse(null);
    }

    @Override
    public Conversation getConversationByUserId1AndUserId2AndPropertyId(Long userId1, Long userId2, Long propertyId) {
        Optional<Conversation> conversation = conversationRepository.findByTwoUsersAndProperty(userId1, userId2, propertyId);
        return conversation.orElse(null);
    }

    @Override
    public Page<Conversation> getConversationsByUserId(Long userId, Pageable pageable) {
        return conversationRepository.findByUsersId(userId, pageable);
    }
}

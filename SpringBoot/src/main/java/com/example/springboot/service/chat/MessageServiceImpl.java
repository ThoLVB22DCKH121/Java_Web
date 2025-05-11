package com.example.springboot.service.chat;

import com.example.springboot.repository.MessageRepository;
import com.example.springboot.repository.entity.Conversation;
import com.example.springboot.repository.entity.Message;
import com.example.springboot.repository.entity.User;
import com.example.springboot.service.ConversationService;
import com.example.springboot.service.MessageService;
import com.example.springboot.service.auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private ConversationService conversationService;

    @Override
    public List<Message> getMessagesForConversation(Long conversationId) {
        return messageRepository.findByConversationIdOrderBySendAtAsc(conversationId);
    }

    @Override
    public void sendMessage(Long conversationId, String sender, String content) {
        Conversation conversation = conversationService.getConversationById(conversationId);
        if (conversation == null) {
            throw new IllegalArgumentException("Conversation not found: " + conversationId);
        }
        Message message = new Message();
        message.setConversation(conversation);
        message.setSender(sender);
        message.setContent(content);
        Message savedMessage = messageRepository.save(message);
        messagingTemplate.convertAndSend("/topic/" + conversationId, savedMessage);
    }
}
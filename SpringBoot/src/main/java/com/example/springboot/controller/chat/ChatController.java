package com.example.springboot.controller.chat;

import com.example.springboot.models.ChatMessage;
import com.example.springboot.repository.ChatHistoryRepository;
import com.example.springboot.repository.entity.ChatHistory;
import com.example.springboot.service.Impl.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private ChatHistoryRepository chatHistoryRepository;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessage chatMessage, Principal principal) {
        System.out.println("Received message: " + chatMessage);

        String senderUsername = principal.getName();
        System.out.println("Sender username from Principal: " + senderUsername);

        UserDetails sender = appUserService.loadUserByUsername(senderUsername);
        System.out.println("Sender loaded: " + sender);

        UserDetails recipient;
        try {
            recipient = appUserService.loadUserByUsername(chatMessage.getRecipient());
            System.out.println("Recipient loaded: " + recipient);
        } catch (Exception e) {
            System.out.println("Error loading recipient: " + chatMessage.getRecipient() + ", error: " + e.getMessage());
            return;
        }

        String channel;
        if (senderUsername.equals("admin")) {
            channel = "chat-" + chatMessage.getRecipient();
        } else {
            channel = "chat-" + senderUsername;
        }

        chatMessage.setSender(senderUsername);
        chatMessage.setChannel(channel);

        ChatHistory history = new ChatHistory(senderUsername, chatMessage.getRecipient(), chatMessage.getContent(), channel);
        chatHistoryRepository.save(history);
        System.out.println("Saved message to channel: " + channel);

        System.out.println("Sending message to recipient: " + chatMessage.getRecipient() + ", content: " + chatMessage.getContent());
        messagingTemplate.convertAndSendToUser(chatMessage.getRecipient(), "/queue/messages", chatMessage);
    }
}
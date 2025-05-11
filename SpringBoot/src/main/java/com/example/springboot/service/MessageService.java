package com.example.springboot.service;

import com.example.springboot.repository.entity.Message;

import java.util.List;

public interface MessageService {
    public List<Message> getMessagesForConversation(Long conversationId);
    public void sendMessage(Long conversationId, String sender, String content);
}

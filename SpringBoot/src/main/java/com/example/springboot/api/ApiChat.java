package com.example.springboot.api;

import com.example.springboot.repository.ChatHistoryRepository;
import com.example.springboot.repository.entity.ChatHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiChat {
    @Autowired
    private ChatHistoryRepository chatHistoryRepository;

    @GetMapping("/api/chat/history/{channel}")
    public ResponseEntity<List<ChatHistory>> getChatHistory(@PathVariable String channel) {
        System.out.println("Fetching history for channel: " + channel);
        List<ChatHistory> history = chatHistoryRepository.findByChannel(channel);
        System.out.println("Found " + history.size() + " messages");
        return ResponseEntity.ok(history);
    }
}

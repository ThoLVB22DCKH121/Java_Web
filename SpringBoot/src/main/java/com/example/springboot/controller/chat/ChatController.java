package com.example.springboot.controller.chat;

import com.example.springboot.converter.ChatMessageDTOConverter;
import com.example.springboot.models.ChatMessageDTO;
import com.example.springboot.models.ConversationDetailsDTO;
import com.example.springboot.models.ConversationSummaryDTO;
import com.example.springboot.models.PropertyDTO;
import com.example.springboot.repository.MessageRepository;
import com.example.springboot.repository.entity.Message;
import com.example.springboot.repository.entity.Conversation;
import com.example.springboot.repository.entity.User;
import com.example.springboot.service.ConversationService;
import com.example.springboot.service.auth.UserDetailsImpl;
import com.example.springboot.service.auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class ChatController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ChatMessageDTOConverter chatMessageDTOConverter;
    @Autowired
    private ConversationService conversationService;
    @Autowired
    private UserService userService;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessageDTO chatMessageDTO) {
        Message message = chatMessageDTOConverter.toMessage(chatMessageDTO);
        message.setConversation(conversationService.getConversationById(chatMessageDTO.getConversationId()));
        messageRepository.save(message);
        messagingTemplate.convertAndSend("/topic/conversation/" + chatMessageDTO.getConversationId(), chatMessageDTO);
        messagingTemplate.convertAndSend("/topic/conversations", chatMessageDTO);
    }

    @PostMapping("/conversations")
    public ResponseEntity<Conversation> createConversation(
            @RequestBody Map<String, Long> request,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Long userId1 = userDetails.getId();
        Long userId2 = request.get("userId2");
        Long propertyId = request.get("propertyId");

        if (userId1 == null || userId2 == null || propertyId == null) {
            return ResponseEntity.badRequest().body(null);
        }

        if (userId1.equals(userId2)) {
            return ResponseEntity.badRequest().body(null);
        }

        Conversation existingConversation = conversationService.getConversationByUserId1AndUserId2AndPropertyId(userId1, userId2, propertyId);
        if (existingConversation != null) {
            return ResponseEntity.ok(existingConversation);
        }

        Conversation conversation = conversationService.createConversation(userId1, userId2, propertyId);
        return ResponseEntity.status(HttpStatus.CREATED).body(conversation);
    }

    @GetMapping("/conversation")
    public String chatPage(Model model) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("senderName", userDetails.getFullName());
        return "chat";
    }

    @GetMapping("/conversations")
    public ResponseEntity<List<ConversationSummaryDTO>> getConversations(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword) {
        try {
            if (userDetails == null || userDetails.getId() == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.emptyList());
            }
            User user = userService.getUserById(userDetails.getId());
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
            }

            Pageable pageable = PageRequest.of(page, size);
            Page<Conversation> conversationPage = conversationService.getConversationsByUserId(userDetails.getId(), pageable);
            List<ConversationSummaryDTO> summaries = conversationPage.getContent().stream()
                    .map(conv -> {
                        ConversationSummaryDTO dto = new ConversationSummaryDTO();
                        dto.setId(conv.getId());
                        User otherUser = conv.getUsers().stream()
                                .filter(u -> !u.getId().equals(userDetails.getId()))
                                .findFirst()
                                .orElse(null);
                        String otherUserName = otherUser != null ? otherUser.getFullname() : "Unknown";
                        String propertyName = conv.getProperty() != null ? conv.getProperty().getName() : "Không có tên";
                        // Lọc theo keyword (nếu có)
                        if (keyword != null && !keyword.trim().isEmpty()) {
                            String keywordLower = keyword.trim().toLowerCase();
                            if (!otherUserName.toLowerCase().contains(keywordLower) && !propertyName.toLowerCase().contains(keywordLower)) {
                                return null;
                            }
                        }
                        dto.setOtherUserName(otherUserName);
                        dto.setPropertyName(propertyName);
                        List<Message> messages = conv.getMessages() != null ? conv.getMessages() : Collections.emptyList();
                        if (!messages.isEmpty()) {
                            Message lastMsg = messages.get(messages.size() - 1);
                            dto.setLastMessage(lastMsg.getContent());
                            dto.setLastMessageTime(lastMsg.getSendAt() != null ? lastMsg.getSendAt().toString() : null);
                        }
                        return dto;
                    })
                    .filter(dto -> dto != null)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(summaries);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    @GetMapping("/conversations/{id}")
    public ResponseEntity<ConversationDetailsDTO> getConversation(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Conversation conversation = conversationService.getConversationById(id);
        if (conversation == null || !conversation.getUsers().stream().anyMatch(u -> u.getId().equals(userDetails.getId()))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        ConversationDetailsDTO dto = new ConversationDetailsDTO();
        dto.setId(conversation.getId());
        Pageable pageable = PageRequest.of(page, size, Sort.by("sendAt").descending());
        Page<Message> messagePage = messageRepository.findByConversationId(id, pageable);
        dto.setMessages(chatMessageDTOConverter.toChatMessageDTOList(messagePage.getContent()));
        dto.setProperty(new PropertyDTO(conversation.getProperty()));
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/uploadFile")
    public ResponseEntity<Map<String, String>> uploadFile(
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "File is empty"));
        }

        long maxSizeInBytes = 10 * 1024 * 1024; // 10MB
        if (file.getSize() > maxSizeInBytes) {
            return ResponseEntity.badRequest().body(Map.of("error", "File size exceeds 10MB limit"));
        }

        try {
            String uploadDir = "uploads/";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String originalFilename = file.getOriginalFilename();
            String sanitizedFilename = originalFilename.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
            String fileName = UUID.randomUUID().toString() + "_" + sanitizedFilename;
            Path filePath = Paths.get(uploadDir, fileName);
            Files.write(filePath, file.getBytes());

            String fileUrl = "/uploads/" + fileName;
            return ResponseEntity.ok(Map.of("fileUrl", fileUrl));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to upload file: " + e.getMessage()));
        }
    }
}
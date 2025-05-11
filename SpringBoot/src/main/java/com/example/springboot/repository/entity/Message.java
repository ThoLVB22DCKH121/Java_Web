package com.example.springboot.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "content")
    private String content;
    @Column(name = "type")
    private String type;
    @Column(name = "file_url")
    private String fileUrl;
    @Column(name = "send_at")
    private String sendAt;
    @Column(name = "sender")
    private String sender;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "conversation_id", nullable = false)
    private Conversation conversation;


    public Message() {}
    public Message(Long id, String content, String type, String fileUrl, Conversation conversation) {
        this.id = id;
        this.content = content;
        this.type = type;
        this.fileUrl = fileUrl;
        this.conversation = conversation;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getFileUrl() {
        return fileUrl;
    }
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
    public Conversation getConversation() {
        return conversation;
    }
    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }
    public String getSendAt() {
        return sendAt;
    }
    public void setSendAt(String sendAt) {
        this.sendAt = sendAt;
    }
    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
    @Override
    public String toString() {
        return "ChatMessageDTO{" +
                "content='" + content + '\'' +
                ", type='" + type + '\'' +
                ", sendAt='" + sendAt + '\'' +
                ", conversationId=" + conversation.getId() +
                ", sender='" + sender + '\'' +
                '}';
    }

}

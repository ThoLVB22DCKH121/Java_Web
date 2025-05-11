package com.example.springboot.models;

public class ChatMessageDTO {
    private Long id;
    private Long conversationId;
    private String type;
    private String content;
    private String sender;
    private String sendAt;
    private String fileUrl; // Added to support file/image URLs

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getConversationId() {
        return conversationId;
    }
    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
    public String getSendAt() {
        return sendAt;
    }
    public void setSendAt(String sendAt) {
        this.sendAt = sendAt;
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

    @Override
    public String toString() {
        return "ChatMessageDTO{" +
                "content='" + content + '\'' +
                ", type='" + type + '\'' +
                ", sendAt='" + sendAt + '\'' +
                ", conversationId=" + conversationId +
                ", sender='" + sender + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                '}';
    }
}
package com.example.springboot.models;

import java.io.Serializable;

public class ChatMessage implements Serializable {
    private String sender;
    private String recipient;
    private String content;
    private String channel;

    // Constructors
    public ChatMessage() {}

    public ChatMessage(String sender, String recipient, String content, String channel) {
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
        this.channel = channel;
    }

    // Getters và setters
    public String getSender() {
        return sender;
    }

    public void setSenderUsername(String senderUsername) {
        this.sender = senderUsername;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return "ChatMessage{senderUsername='" + sender + "', recipient='" + recipient + "', content='" + content + "', channel='" + channel + "'}";
    }
}
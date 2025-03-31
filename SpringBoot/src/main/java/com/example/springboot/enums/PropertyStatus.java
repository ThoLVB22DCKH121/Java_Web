package com.example.springboot.enums;

public enum PropertyStatus {
    AVAILABLE("Còn bán"),
    SOLD("Đã bán"),
    RESERVED("Đặt trước");

    private final String displayName;

    PropertyStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

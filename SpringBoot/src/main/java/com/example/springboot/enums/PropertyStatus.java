package com.example.springboot.enums;

public enum PropertyStatus {
    AVAILABLE("Chưa đặt cọc"),
    SOLD("Đã bán"),
    RESERVED("Đã đặt cọc");

    private final String displayName;

    PropertyStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

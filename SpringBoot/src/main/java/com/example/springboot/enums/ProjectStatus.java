package com.example.springboot.enums;

public enum ProjectStatus {
    UNDER_CONSTRUCTION("Đang xây dựng"),
    COMPLETED("Hoàn thành");


    private final String displayName;
    ProjectStatus(String displayName) {
        this.displayName = displayName;
    }
    public String getDisplayName() {
        return displayName;
    }
}
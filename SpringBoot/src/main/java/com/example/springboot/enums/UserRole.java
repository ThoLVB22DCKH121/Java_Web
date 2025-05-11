package com.example.springboot.enums;

public enum UserRole {
    ADMIN("Quản trị viên"),
    EMPLOYEE("Nhân viên"),
    CUSTOMER("Người dùng");

    private final String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

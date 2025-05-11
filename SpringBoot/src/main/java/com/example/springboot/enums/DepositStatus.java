package com.example.springboot.enums;

public enum DepositStatus {
    PENDING("Chờ xử lý"),
    SUCCESS("Thành công"),
    FAILED("Thất bại"),
    CANCELLED("Hủy");

    private final String displayName;

    DepositStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

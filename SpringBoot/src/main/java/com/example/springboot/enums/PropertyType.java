package com.example.springboot.enums;

public enum PropertyType {
    CHUNG_CU("Căn hộ chung cư"),
    BIET_THU("Biệt thự"),
    NHA_PHO("Nhà phố"),
    DAT_NEN("Đất nền");

    private final String displayName;

    PropertyType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

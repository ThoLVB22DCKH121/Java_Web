package com.example.springboot.models;

import com.example.springboot.enums.UserRole;

public final class UserDTO {
    private int id;
    private String fullName;
    private String email;
    private String address;
    private String phone;
    private UserRole role;

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullname) {
        this.fullName = fullname;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public UserRole getRole() {
        return role;
    }
    public void setRole(UserRole role) {
        this.role = role;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}

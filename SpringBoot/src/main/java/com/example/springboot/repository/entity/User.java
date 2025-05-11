package com.example.springboot.repository.entity;

import com.example.springboot.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role;

    @Column(name = "phone")
    private String phone;

    @Column(name = "createAt")
    private Date createAt;

    @Column(name = "address")
    private String address;


    @JsonIgnore
    @ManyToMany(mappedBy = "users")
    private Set<Conversation> conversations;

    @OneToMany(mappedBy = "user")
    private List<Property> propertyList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Deposit> transactions = new ArrayList<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "favourite",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "property_id")
    )
    private Set<Property> favouriteProperties = new HashSet<>();

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFullname() {
        return fullname;
    }
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public UserRole getRole() {
        return role;
    }
    public void setRole(UserRole role) {
        this.role = role;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public Date getCreateAt() {
        return createAt;
    }
    public void setCreateAt(Date createAt) {
        this.createAt = new Date();
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Set<Conversation> getConversations() {
        return conversations;
    }
    public void setConversations(Set<Conversation> conversations) {
        this.conversations = conversations;
    }
    public List<Property> getPropertyList() {
        return propertyList;
    }
    public void setPropertyList(List<Property> propertyList) {
        this.propertyList = propertyList;
    }
    public List<Deposit> getTransactions() {
        return transactions;
    }
    public void setTransactions(List<Deposit> transactions) {
        this.transactions = transactions;
    }
    public Set<Property> getFavouriteProperties() {
        return favouriteProperties;
    }
    public void setFavouriteProperties(Set<Property> favouriteProperties) {
        this.favouriteProperties = favouriteProperties;
    }
}

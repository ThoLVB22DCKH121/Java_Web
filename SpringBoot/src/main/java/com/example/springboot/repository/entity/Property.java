package com.example.springboot.repository.entity;

import com.example.springboot.enums.PropertyStatus;
import com.example.springboot.enums.PropertyType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "properties")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private PropertyType type;
    @Column(name = "area")
    private Long area;
    @Column(name = "price")
    private Long price;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PropertyStatus status;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(name = "mapurl",columnDefinition = "TEXT")
    private String mapUrl;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "create_at")
    private Date createAt;
    @Column(name = "images",columnDefinition = "TEXT")
    private String images;
    @Column(name = "address")
    private String address;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "property")
    private List<Conversation> conversations;

    @JsonIgnore
    @ManyToMany(mappedBy = "favouriteProperties")
    private Set<User> favouritedByUsers = new HashSet<>();

    public List<String> getImageList() {
        if (images == null || images.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.stream(images.split(","))
                .map(String::trim)
                .filter(url -> !url.isEmpty())
                .collect(Collectors.toList());
    }

    @OneToMany(mappedBy = "property")
    private List<Deposit> deposits;

    public void setImages(List<String> imageList) {
        if (imageList == null || imageList.isEmpty()) {
            this.images = null;
        } else {
            this.images = String.join(",", imageList);
        }
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public PropertyType getType() {
        return type;
    }
    public void setType(PropertyType type) {
        this.type = type;
    }
    public Long getArea() {
        return area;
    }
    public void setArea(Long area) {
        this.area = area;
    }
    public Long getPrice() {
        return price;
    }
    public void setPrice(Long price) {
        this.price = price;
    }
    public PropertyStatus getStatus() {
        return status;
    }
    public void setStatus(PropertyStatus status) {
        this.status = status;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    public String getMapUrl() {
        return mapUrl;
    }
    public void setMapUrl(String mapUrl) {
        this.mapUrl = mapUrl;
    }
    public String getImages() {
        return images;
    }
    public void setImages(String images) {
        this.images = images;
    }
    public Date getCreateAt() {
        return createAt;
    }
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public List<Conversation> getConversations() {
        return conversations;
    }
    public void setConversations(List<Conversation> conversations) {
        this.conversations = conversations;
    }
    public Set<User> getFavouritedByUsers() {
        return favouritedByUsers;
    }
    public void setFavouritedByUsers(Set<User> favouritedByUsers) {
        this.favouritedByUsers = favouritedByUsers;
    }
    public List<Deposit> getDeposits() {
        return deposits;
    }
    public void setDeposits(List<Deposit> deposits) {
        this.deposits = deposits;
    }
}

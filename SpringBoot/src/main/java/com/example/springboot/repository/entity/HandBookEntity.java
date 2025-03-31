package com.example.springboot.repository.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "handbooks")
public class HandBookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "category")
    private String category;
    @Column(name = "date")
    private Date date;
    @Column(name = "created_by")
    private String createdBy;

    @OneToMany(mappedBy = "handbook")
    private List<HandBookDetailEntity> handBookDetails = new ArrayList<>();

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public List<HandBookDetailEntity> getHandBookDetails() {
        return handBookDetails;
    }
    public void setHandBookDetails(List<HandBookDetailEntity> handBookDetails) {
        this.handBookDetails = handBookDetails;
    }
    public String getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}

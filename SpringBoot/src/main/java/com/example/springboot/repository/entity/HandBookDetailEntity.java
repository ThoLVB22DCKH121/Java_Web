package com.example.springboot.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
@Entity
@Table(name = "handbookdetail")
public class HandBookDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;
    @Column(name = "image")
    private String image;

    @ManyToOne
    @JoinColumn(name = "handbookid")
    @JsonIgnore
    private HandBookEntity handbook;

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
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public HandBookEntity getHandbook() {
        return handbook;
    }
    public void setHandbook(HandBookEntity handbook) {
        this.handbook = handbook;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
}

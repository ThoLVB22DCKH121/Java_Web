package com.example.springboot.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "newsdetail")
public class NewsDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "content",columnDefinition = "TEXT")
    private String content;
    @Column(name = "image")
    private String image;

    @ManyToOne
    @JoinColumn(name = "newsid")
    @JsonIgnore
    private NewsEntity news;

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
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public NewsEntity getNews() {
        return news;
    }
    public void setNews(NewsEntity news) {
        this.news = news;
    }
}

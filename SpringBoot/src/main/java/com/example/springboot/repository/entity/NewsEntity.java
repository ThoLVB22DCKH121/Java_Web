package com.example.springboot.repository.entity;

import com.example.springboot.converter.UserDTOConverter;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="news")
public class NewsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "date")
    private Date date;
    @Column(name = "created_by")
    private String createdBy;

    @OneToMany(mappedBy = "news")
    List<NewsDetailEntity> newsDetails = new ArrayList<>();

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
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public List<NewsDetailEntity> getNewsDetails() {
        return newsDetails;
    }
    public void setNewsDetails(List<NewsDetailEntity> newsDetails) {
        this.newsDetails = newsDetails;
    }
    public String getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}

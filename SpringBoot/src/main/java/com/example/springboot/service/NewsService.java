package com.example.springboot.service;

import com.example.springboot.repository.entity.NewsEntity;

import java.util.List;

public interface NewsService {
    public List<NewsEntity> getAllNews();
    public NewsEntity getNewsById(Long id);
    public NewsEntity createNews(NewsEntity news);
    public void updateNews(Long id, NewsEntity news);
    public void deleteNews(Long id);
}

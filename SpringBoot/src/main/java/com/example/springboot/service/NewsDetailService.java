package com.example.springboot.service;

import com.example.springboot.repository.entity.NewsDetailEntity;

import java.util.List;

public interface NewsDetailService {
    public List<NewsDetailEntity> getAllNewsDetail();
    public void updateNewsDetail(NewsDetailEntity newsDetail,Long idNewsDetail);
    public void deleteNewsDetail(Long idNewsDetail);
    public void createNewsDetailForNews(NewsDetailEntity newsDetail,Long idNews);
}

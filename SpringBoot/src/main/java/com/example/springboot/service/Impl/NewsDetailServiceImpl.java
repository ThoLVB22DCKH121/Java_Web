package com.example.springboot.service.Impl;

import com.example.springboot.repository.NewsDetailRepository;
import com.example.springboot.repository.NewsRepository;
import com.example.springboot.repository.entity.NewsDetailEntity;
import com.example.springboot.repository.entity.NewsEntity;
import com.example.springboot.service.NewsDetailService;
import com.example.springboot.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NewsDetailServiceImpl implements NewsDetailService {
    @Autowired
    private NewsDetailRepository newsDetailRepository;
    @Autowired
    private NewsRepository newsRepository;
    @Override
    public List<NewsDetailEntity> getAllNewsDetail() {
        return newsDetailRepository.findAll();
    }

    @Override
    public void updateNewsDetail(NewsDetailEntity updatedNewsDetail, Long idNewsDetail) {
        Optional<NewsDetailEntity> newsDetailEntity = newsDetailRepository.findById(idNewsDetail);
        if (newsDetailEntity.isPresent()) {
            NewsDetailEntity newsDetail = newsDetailEntity.get();
            newsDetail.setTitle(updatedNewsDetail.getTitle());
            newsDetail.setContent(updatedNewsDetail.getContent());
            newsDetail.setImage(updatedNewsDetail.getImage());
            newsDetailRepository.save(newsDetail);
        }
    }

    @Override
    public void deleteNewsDetail(Long idNewsDetail) {
        newsDetailRepository.deleteById(idNewsDetail);
    }

    @Override
    public void createNewsDetailForNews(NewsDetailEntity newsDetail, Long idNews) {
        Optional<NewsEntity> newsEntity = newsRepository.findById(idNews);
        if(newsDetail!=null && newsEntity.isPresent()) {
            newsDetail.setNews(newsEntity.get());
            newsDetailRepository.save(newsDetail);
        }
    }
}

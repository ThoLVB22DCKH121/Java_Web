package com.example.springboot.service.Impl;

import com.example.springboot.converter.UserDTOConverter;
import com.example.springboot.repository.AppUserRepository;
import com.example.springboot.repository.NewsRepository;
import com.example.springboot.repository.entity.NewsEntity;
import com.example.springboot.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private UserDTOConverter userDTOConverter;

    @Override
    public List<NewsEntity> getAllNews() {
        return newsRepository.findAll();
    }

    @Override
    public NewsEntity getNewsById(Long id) {
        return newsRepository.findById(id).orElse(null);
    }

    @Override
    public NewsEntity createNews(NewsEntity news) {
        Integer id=((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        news.setDate(new Date());
        return newsRepository.save(news);
    }

    @Override
    public void updateNews(Long id, NewsEntity updatedNews) {
        Optional<NewsEntity> newsEntity = newsRepository.findById(id);
        if (newsEntity.isPresent()) {
            NewsEntity news = newsEntity.get();
            news.setTitle(updatedNews.getTitle());
            newsRepository.save(news);
        }
    }

    @Override
    public void deleteNews(Long id) {
        newsRepository.deleteById(id);
    }
}

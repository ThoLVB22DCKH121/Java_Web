package com.example.springboot.service;

import com.example.springboot.repository.entity.HandBookEntity;

import java.util.List;

public interface HandBookService {
    public List<HandBookEntity> getAllHandBooks();
    public void createHandBook(HandBookEntity handBook);
    public HandBookEntity getHandBookById(Long id);
    public void updateHandBook(HandBookEntity handBook,Long idHandBook);
}

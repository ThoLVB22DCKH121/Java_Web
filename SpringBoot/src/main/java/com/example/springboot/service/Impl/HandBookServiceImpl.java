package com.example.springboot.service.Impl;

import com.example.springboot.repository.HandBookRepository;
import com.example.springboot.repository.entity.HandBookEntity;
import com.example.springboot.service.HandBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class HandBookServiceImpl implements HandBookService {
    @Autowired
    HandBookRepository handBookRepository;

    @Override
    public List<HandBookEntity> getAllHandBooks() {
        return handBookRepository.findAll();
    }

    @Override
    public void createHandBook(HandBookEntity handBook) {
        handBook.setDate(new Date());
        handBookRepository.save(handBook);
    }

    @Override
    public HandBookEntity getHandBookById(Long id) {
        return handBookRepository.findById(id).orElse(null);
    }

    @Override
    public void updateHandBook(HandBookEntity updatedHandBook, Long idHandBook) {
        Optional<HandBookEntity> handBookEntity = handBookRepository.findById(idHandBook);
        if (handBookEntity.isPresent()) {
            HandBookEntity handBook = handBookEntity.get();
            handBook.setTitle(updatedHandBook.getTitle());
            handBook.setDate(updatedHandBook.getDate());
            handBook.setCategory(updatedHandBook.getCategory());
            handBookRepository.save(handBook);
        }
    }
}

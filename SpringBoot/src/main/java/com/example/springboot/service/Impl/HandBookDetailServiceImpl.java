package com.example.springboot.service.Impl;

import com.example.springboot.repository.HandBookDetailRepository;
import com.example.springboot.repository.HandBookRepository;
import com.example.springboot.repository.entity.HandBookDetailEntity;
import com.example.springboot.repository.entity.HandBookEntity;
import com.example.springboot.service.HandBookDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HandBookDetailServiceImpl implements HandBookDetailService {
    @Autowired
    HandBookDetailRepository handBookDetailRepository;
    @Autowired
    HandBookRepository handBookRepository;

    @Override
    public List<HandBookDetailEntity> getAllHandBookDetails() {
        return handBookDetailRepository.findAll();
    }

    @Override
    public void createHandBookDetailForHandBook(Long idHandBook, HandBookDetailEntity handBookDetail) {
        Optional<HandBookEntity> handBook = handBookRepository.findById(idHandBook);
        if (handBookDetail != null && handBook.isPresent()) {
            handBookDetail.setHandbook(handBook.get());
            handBookDetailRepository.save(handBookDetail);
        }
    }

    @Override
    public void deleteHandBookDetail(Long idHandBookDetail) {
        handBookDetailRepository.deleteById(idHandBookDetail);
    }

    @Override
    public void updateHandBookDetail(Long idHandBookDetail, HandBookDetailEntity updatedHandBookDetail) {
        Optional<HandBookDetailEntity> handBookDetailEntity = handBookDetailRepository.findById(idHandBookDetail);
        if (handBookDetailEntity.isPresent()) {
            HandBookDetailEntity handBookDetail = handBookDetailEntity.get();
            handBookDetail.setTitle(updatedHandBookDetail.getTitle());
            handBookDetail.setContent(updatedHandBookDetail.getContent());
            handBookDetail.setImage(updatedHandBookDetail.getImage());
            handBookDetailRepository.save(handBookDetail);
        }
    }
}

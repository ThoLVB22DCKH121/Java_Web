package com.example.springboot.service;

import com.example.springboot.repository.entity.HandBookDetailEntity;

import java.util.List;

public interface HandBookDetailService {
    public List<HandBookDetailEntity> getAllHandBookDetails();
    public void createHandBookDetailForHandBook(Long idHandBook,HandBookDetailEntity handBookDetail);
    public void deleteHandBookDetail(Long idHandBookDetail);
    public void updateHandBookDetail(Long idHandBookDetail,HandBookDetailEntity handBookDetail);
}

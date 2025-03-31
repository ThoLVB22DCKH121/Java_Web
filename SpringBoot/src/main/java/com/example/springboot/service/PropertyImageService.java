package com.example.springboot.service;

import com.example.springboot.repository.entity.PropertyImageEntity;

import java.util.List;

public interface PropertyImageService {
    public List<PropertyImageEntity> getAllPropertyImages();
    public void createPropertyImage(Long id, PropertyImageEntity propertyImage);
    public void deleteImage(Long id);
}

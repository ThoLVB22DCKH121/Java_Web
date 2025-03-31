package com.example.springboot.service.Impl;

import com.example.springboot.repository.PropertyImageRepository;
import com.example.springboot.repository.PropertyRepository;
import com.example.springboot.repository.entity.PropertyEntity;
import com.example.springboot.repository.entity.PropertyImageEntity;
import com.example.springboot.service.PropertyImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyImageServiceImpl implements PropertyImageService {
    @Autowired
    private PropertyImageRepository propertyImageRepository;
    @Autowired
    private PropertyRepository propertyRepository;
    @Override
    public List<PropertyImageEntity> getAllPropertyImages() {
        return propertyImageRepository.findAll();
    }

    @Override
    public void createPropertyImage(Long idProperty, PropertyImageEntity propertyImage) {
        Optional<PropertyEntity> property = propertyRepository.findById(idProperty);
        propertyImage.setProperty(property.get());
        propertyImageRepository.save(propertyImage);
    }

    @Override
    public void deleteImage(Long id) {
        propertyImageRepository.deleteById(id);
    }
}

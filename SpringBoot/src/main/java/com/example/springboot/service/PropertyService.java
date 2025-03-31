package com.example.springboot.service;

import com.example.springboot.repository.entity.PropertyEntity;

import java.util.List;

public interface PropertyService {
    public List<PropertyEntity> getAllProperties();
    public PropertyEntity getPropertyById(int id);
    public void createPropertyForProject(Long id, PropertyEntity property);
    public void deleteProperty(Long id);
    public void updateProperty(Long idProperty, PropertyEntity property);
}

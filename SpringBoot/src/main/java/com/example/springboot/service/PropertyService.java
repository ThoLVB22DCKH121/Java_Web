package com.example.springboot.service;

import com.example.springboot.enums.PropertyStatus;
import com.example.springboot.enums.PropertyType;
import com.example.springboot.repository.entity.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PropertyService {
    public Property getPropertyById(Long id);
    public Property createProperty(Property property);
    public void deleteProperty(Long id);
    public Property updateProperty(Property property);
    public Property updatePropertyStatus(Long propertyId, PropertyStatus propertyStatus);
    public Page<Property> searchProperty(Pageable pageable, Long propertyId, PropertyType type, Double minArea, Double maxArea,
                                         Double minPrice, Double maxPrice, PropertyStatus status, String address, Long staffId);
    public void assignmentProperty(Long propertyId, Long userId);
    public void addFavourite(Long propertyId, Long userId);
    public void removeFavourite(Long propertyId, Long userId);
    public boolean isFavourite(Property property, Long userId);
    public Property uploadImage(MultipartFile file,Long propertyId);
}

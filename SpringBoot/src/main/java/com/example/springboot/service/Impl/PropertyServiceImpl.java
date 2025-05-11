package com.example.springboot.service.Impl;

import com.example.springboot.enums.PropertyStatus;
import com.example.springboot.enums.PropertyType;
import com.example.springboot.repository.PropertyRepository;
import com.example.springboot.repository.UserRepository;
import com.example.springboot.repository.entity.Property;
import com.example.springboot.repository.entity.User;
import com.example.springboot.service.PropertyService;
import com.example.springboot.service.auth.UserDetailsImpl;
import com.example.springboot.service.auth.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class PropertyServiceImpl implements PropertyService {
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Property getPropertyById(Long id) {
        Optional<Property> property = propertyRepository.findById(id);
        return property.orElse(null);
    }

    @Override
    public Property createProperty(Property property) {
        String fullname = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getFullName();
        property.setCreatedBy(fullname);
        property.setCreateAt(new Date());
        return propertyRepository.save(property);
    }

    @Override
    public void deleteProperty(Long id) {
        propertyRepository.deleteById(id);
    }

    @Override
    public Property updateProperty(Property updatedProperty) {
        Optional<Property> propertyEntity = propertyRepository.findById(updatedProperty.getId());
        if (propertyEntity.isPresent()) {
            Property property = propertyEntity.get();
            property.setPrice(updatedProperty.getPrice());
            property.setType(updatedProperty.getType());
            property.setStatus(updatedProperty.getStatus());
            property.setImages(updatedProperty.getImages());
            property.setDescription(updatedProperty.getDescription());
            property.setName(updatedProperty.getName());
            property.setAddress(updatedProperty.getAddress());
            property.setArea(updatedProperty.getArea());
            return propertyRepository.save(property);
        }
        return null;
    }

    @Override
    public Property updatePropertyStatus(Long propertyId, PropertyStatus propertyStatus) {
        Property property = getPropertyById(propertyId);
        property.setStatus(propertyStatus);
        return propertyRepository.save(property);
    }

    @Override
    public Page<Property> searchProperty(Pageable pageable, Long propertyId, PropertyType type, Double minArea, Double maxArea, Double minPrice, Double maxPrice, PropertyStatus status, String address, Long staffId) {
        Page<Property> result = propertyRepository.searchProperty(propertyId, type, minArea, maxArea, minPrice, maxPrice, status, address, staffId, pageable);
        return result;
    }

    @Override
    public void assignmentProperty(Long propertyId, Long userId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bất động sản"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));

        property.setUser(user);

        propertyRepository.save(property);
    }

    @Override
    @Transactional
    public void addFavourite(Long propertyId, Long userId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new IllegalArgumentException("Property not found"));
        if (!isFavourite(property, userId)) {
            User user = userService.getUserById(userId);
            user.getFavouriteProperties().add(property);
            userRepository.save(user);
        }
    }

    @Override
    @Transactional
    public void removeFavourite(Long propertyId, Long userId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new IllegalArgumentException("Property not found"));
        if (isFavourite(property, userId)) {
            User user = userService.getUserById(userId);
            user.getFavouriteProperties().remove(property);
            userRepository.save(user);
        }
    }

    @Override
    public boolean isFavourite(Property property, Long userId) {
        User user = userService.getUserById(userId);
        return user.getFavouriteProperties().contains(property);
    }

    @Override
    public Property uploadImage(MultipartFile file, Long propertyId) {
        if (file.isEmpty()) {
            return null;
        }
        long maxSizeInBytes = 10 * 1024 * 1024; // 10MB
        if (file.getSize() > maxSizeInBytes) {
            return null;
        }
        try {
            String uploadDir = "uploads/";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String originalFilename = file.getOriginalFilename();
            String sanitizedFilename = originalFilename.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
            String fileName = UUID.randomUUID().toString() + "_" + sanitizedFilename;
            Path filePath = Paths.get(uploadDir, fileName);
            Files.write(filePath, file.getBytes());

            String fileUrl = "/uploads/" + fileName;
            Property property = propertyRepository.findById(propertyId).get();
            List<String> images = property.getImageList();
            images.add(fileUrl);
            property.setImages(images);
            return propertyRepository.save(property);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

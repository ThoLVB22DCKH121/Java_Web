package com.example.springboot.service.Impl;

import com.example.springboot.repository.ProjectRepository;
import com.example.springboot.repository.PropertyRepository;
import com.example.springboot.repository.entity.ProjectEntity;
import com.example.springboot.repository.entity.PropertyEntity;
import com.example.springboot.service.ProjectService;
import com.example.springboot.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyServiceImpl implements PropertyService {
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Override
    public List<PropertyEntity> getAllProperties() {
        return propertyRepository.findAll();
    }

    @Override
    public PropertyEntity getPropertyById(int id) {
        return null;
    }

    @Override
    public void createPropertyForProject(Long id, PropertyEntity property) {
        Optional<ProjectEntity> project = projectRepository.findById(id);
        if (property != null) {
            property.setProject(project.get());
            propertyRepository.save(property);
        }
    }

    @Override
    public void deleteProperty(Long id) {
        propertyRepository.deleteById(id);
    }

    @Override
    public void updateProperty(Long idProperty, PropertyEntity updatedProperty) {
        Optional<PropertyEntity> propertyEntity = propertyRepository.findById(idProperty);
        if (propertyEntity.isPresent()) {
            PropertyEntity property = propertyEntity.get();
            property.setPrice(updatedProperty.getPrice());
            property.setType(updatedProperty.getType());
            property.setStatus(updatedProperty.getStatus());
            propertyRepository.save(property);
        }
    }
}

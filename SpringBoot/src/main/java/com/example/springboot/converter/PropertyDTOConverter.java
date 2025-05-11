package com.example.springboot.converter;

import com.example.springboot.models.PropertyDTO;
import com.example.springboot.repository.entity.Property;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PropertyDTOConverter {
    @Autowired
    private ModelMapper modelMapper;

    public PropertyDTO toDTO(Property property) {
        PropertyDTO propertyDTO = modelMapper.map(property, PropertyDTO.class);
        propertyDTO.setImageList(property.getImageList());
        propertyDTO.setStatus(property.getStatus().getDisplayName());
        return propertyDTO;
    }

    public Property toEntity(PropertyDTO propertyDTO,Property property) {
        property.setImages(propertyDTO.getImageList());
        return property;
    }
}

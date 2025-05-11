package com.example.springboot.controller.admin;

import com.example.springboot.converter.PropertyDTOConverter;
import com.example.springboot.enums.PropertyStatus;
import com.example.springboot.enums.PropertyType;
import com.example.springboot.models.PropertyDTO;
import com.example.springboot.repository.PropertyRepository;
import com.example.springboot.repository.entity.Property;
import com.example.springboot.service.PropertyService;
import com.example.springboot.service.auth.UserDetailsImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/admin/property")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;
    @Autowired
    private PropertyDTOConverter propertyDTOConverter;
    @Autowired
    private PropertyRepository propertyRepository;

    @GetMapping("/search")
    public ResponseEntity<Page<PropertyDTO>> searchAndSortProperties(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) Long propertyId,
            @RequestParam(required = false) PropertyType type,
            @RequestParam(required = false) Double minArea,
            @RequestParam(required = false) Double maxArea,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) PropertyStatus status,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) Long staffId,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<PropertyDTO> propertyPage = propertyService.searchProperty(
                pageable, propertyId, type, minArea, maxArea, minPrice, maxPrice, status, address, staffId
        ).map(propertyDTOConverter :: toDTO);
        return ResponseEntity.ok(propertyPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Property> getPropertyById(@PathVariable Long id) {
        Property property = propertyService.getPropertyById(id);
        if (property != null) {
            return ResponseEntity.ok(property);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PatchMapping("/{propertyId}/upload")
    public ResponseEntity<Property> uploadImage(
            @RequestParam("file") MultipartFile file,
            @PathVariable Long propertyId) {
        Property property = propertyService.uploadImage(file, propertyId);
        if (property != null) {
            return ResponseEntity.ok(property);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Property> createProperty(@RequestBody Property property) {
        Property createdProperty = propertyService.createProperty(property);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProperty);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Property> updateProperty(@RequestBody Property property) {
        Property updatedProperty = propertyService.updateProperty(property);
        return ResponseEntity.ok(updatedProperty);
    }

    @PatchMapping("/{propertyId}")
    public ResponseEntity<Property> updateStatusProperty(
            @PathVariable Long propertyId,
            @RequestParam PropertyStatus status
    ) {
        System.out.println(status);
        Property property = propertyService.updatePropertyStatus(propertyId, status);
        return ResponseEntity.ok(property);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
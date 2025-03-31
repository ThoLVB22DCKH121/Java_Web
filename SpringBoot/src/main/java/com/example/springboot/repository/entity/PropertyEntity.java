package com.example.springboot.repository.entity;

import com.example.springboot.enums.PropertyStatus;
import com.example.springboot.enums.PropertyType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "properties")
public class PropertyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private PropertyType type;
    @Column(name = "area")
    private Double area;
    @Column(name = "price")
    private Double price;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PropertyStatus status;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(name = "likeproperty")
    private Long likeProperty;
    @Column(name = "created_by")
    private String createdBy;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PropertyImageEntity> images;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public PropertyType getType() {
        return type;
    }
    public void setType(PropertyType type) {
        this.type = type;
    }
    public Double getArea() {
        return area;
    }
    public void setArea(Double area) {
        this.area = area;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public PropertyStatus getStatus() {
        return status;
    }
    public void setStatus(PropertyStatus status) {
        this.status = status;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public ProjectEntity getProject() {
        return project;
    }
    public void setProject(ProjectEntity project) {
        this.project = project;
    }
    public List<PropertyImageEntity> getImages() {
        return images;
    }
    public void setImages(List<PropertyImageEntity> images) {
        this.images = images;
    }
    public Long getLikeProperty() {
        return likeProperty;
    }
    public void setLikeProperty(Long likeProperty) {
        this.likeProperty = likeProperty;
    }
    public String getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}

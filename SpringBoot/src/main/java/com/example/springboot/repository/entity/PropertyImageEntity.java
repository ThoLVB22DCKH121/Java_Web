package com.example.springboot.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "property_images")
public class PropertyImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "image")
    private String image;
    @ManyToOne
    @JoinColumn(name = "property_id")
    @JsonIgnore
    private PropertyEntity property;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public PropertyEntity getProperty() {
        return property;
    }
    public void setProperty(PropertyEntity property) {
        this.property = property;
    }
}

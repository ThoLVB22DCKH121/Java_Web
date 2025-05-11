package com.example.springboot.models;

import com.example.springboot.repository.entity.Property;

import java.util.List;
public class PropertyDTO {
    private Long id;
    private String name;
    private String address;
    private Long area;
    private Long price;
    private String description;
    private String status;
    private List<String> imageList;

    public PropertyDTO(Property property) {
        this.id = property.getId();
        this.address = property.getAddress();
        this.area = property.getArea();
        this.price = property.getPrice();
        this.description = property.getDescription();
        this.imageList = property.getImageList();
    }

    public PropertyDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getArea() {
        return area;
    }

    public void setArea(Long area) {
        this.area = area;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}

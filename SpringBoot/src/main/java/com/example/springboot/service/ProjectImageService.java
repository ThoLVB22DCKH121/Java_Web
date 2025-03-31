package com.example.springboot.service;

import com.example.springboot.repository.entity.ProjectImageEntity;

import java.util.List;

public interface ProjectImageService {
    public List<ProjectImageEntity> getAllProjectImages();
    public void createProjectImage(Long id,ProjectImageEntity projectImage);
    public void deleteImage(Long id);
}

package com.example.springboot.service.Impl;

import com.example.springboot.repository.ProjectImageRepository;
import com.example.springboot.repository.ProjectRepository;
import com.example.springboot.repository.entity.ProjectEntity;
import com.example.springboot.repository.entity.ProjectImageEntity;
import com.example.springboot.service.ProjectImageService;
import com.example.springboot.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectImageServiceImpl implements ProjectImageService {
    @Autowired
    private ProjectImageRepository projectImageRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Override
    public List<ProjectImageEntity> getAllProjectImages() {
        return projectImageRepository.findAll();
    }

    @Override
    public void createProjectImage(Long idProject,ProjectImageEntity projectImage) {
        Optional<ProjectEntity> project = projectRepository.findById(idProject);
        projectImage.setProject(project.get());
        projectImageRepository.save(projectImage);
    }


    @Override
    public void deleteImage(Long id) {
        projectImageRepository.deleteById(id);
    }
}

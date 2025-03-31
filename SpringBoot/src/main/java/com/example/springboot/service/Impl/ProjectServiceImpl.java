package com.example.springboot.service.Impl;

import com.example.springboot.repository.ProjectRepository;
import com.example.springboot.repository.WardRepository;
import com.example.springboot.repository.entity.ProjectEntity;
import com.example.springboot.repository.entity.WardEntity;
import com.example.springboot.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private WardRepository wardRepository;


    @Override
    public List<ProjectEntity> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public void createProject(ProjectEntity project) {
        projectRepository.save(project);
    }

    @Override
    public ProjectEntity getProjectById(Long id) {
        return projectRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    @Override
    public void updateProject(Long id, ProjectEntity updatedProject,String wardCode) {
        Optional<ProjectEntity> projectEntity = projectRepository.findById(id);
        Optional<WardEntity> wardEntity = wardRepository.getWardEntityByCode(wardCode);
        if (projectEntity.isPresent()) {
            ProjectEntity project = projectEntity.get();
            project.setName(updatedProject.getName());
            project.setDescription(updatedProject.getDescription());
            if(wardEntity.isPresent())project.setWard(wardEntity.get());
            project.setStatus(updatedProject.getStatus());
            project.setInvestorAddress(updatedProject.getInvestorAddress());
            project.setInvestorPhone(updatedProject.getInvestorPhone());
            project.setInvestorEmail(updatedProject.getInvestorEmail());
            project.setStartDate(updatedProject.getStartDate());
            projectRepository.save(project);
        }
    }
}

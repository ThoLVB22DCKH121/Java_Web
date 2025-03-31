package com.example.springboot.service;

import com.example.springboot.repository.entity.ProjectEntity;
import com.example.springboot.repository.entity.PropertyEntity;

import java.util.List;
public interface ProjectService {
    public List<ProjectEntity>  getAllProjects();
    public void createProject(ProjectEntity project);
    public ProjectEntity getProjectById(Long id);
    public void deleteProject(Long id);
    public void updateProject(Long id, ProjectEntity project, String wardCode);
}

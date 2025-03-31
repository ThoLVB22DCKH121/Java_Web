package com.example.springboot.repository;

import com.example.springboot.repository.entity.ProjectImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectImageRepository extends JpaRepository<ProjectImageEntity,Long> {
}

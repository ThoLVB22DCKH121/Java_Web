package com.example.springboot.repository;

import com.example.springboot.repository.entity.PropertyImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyImageRepository extends JpaRepository<PropertyImageEntity,Long> {
}

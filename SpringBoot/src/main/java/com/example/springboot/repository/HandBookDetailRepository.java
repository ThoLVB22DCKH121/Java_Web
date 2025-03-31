package com.example.springboot.repository;

import com.example.springboot.repository.entity.HandBookDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HandBookDetailRepository extends JpaRepository<HandBookDetailEntity, Long> {
}

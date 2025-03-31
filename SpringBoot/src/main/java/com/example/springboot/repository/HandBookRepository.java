package com.example.springboot.repository;

import com.example.springboot.repository.entity.HandBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HandBookRepository extends JpaRepository<HandBookEntity,Long> {
}

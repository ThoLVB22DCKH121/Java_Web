package com.example.springboot.repository;

import com.example.springboot.repository.entity.NewsDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsDetailRepository extends JpaRepository<NewsDetailEntity,Long> {
}

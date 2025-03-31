package com.example.springboot.repository;

import com.example.springboot.repository.entity.ProvinceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProvinceRepository extends JpaRepository<ProvinceEntity,Long> {
}

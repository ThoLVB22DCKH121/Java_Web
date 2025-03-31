package com.example.springboot.repository;

import com.example.springboot.repository.entity.DistrictEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DistrictRepository extends JpaRepository<DistrictEntity,Long> {
    List<DistrictEntity> findByProvinceCode(String provinceCode);
}

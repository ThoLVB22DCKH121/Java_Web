package com.example.springboot.repository;

import com.example.springboot.repository.entity.WardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WardRepository extends JpaRepository<WardEntity,Long> {
    List<WardEntity> findByDistrictCode(String districtCode);

    Optional<WardEntity> getWardEntityByCode(String code);
}

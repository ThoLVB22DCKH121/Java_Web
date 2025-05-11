package com.example.springboot.repository;

import com.example.springboot.enums.PropertyStatus;
import com.example.springboot.enums.PropertyType;
import com.example.springboot.repository.entity.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    @Query("SELECT p FROM Property p WHERE " +
            "(:propertyId IS NULL OR p.id = :propertyId) AND " +
            "(:type IS NULL OR p.type = :type) AND " +
            "(:minArea IS NULL OR p.area >= :minArea) AND " +
            "(:maxArea IS NULL OR p.area <= :maxArea) AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
            "(:status IS NULL OR p.status = :status) AND " +
            "(:address IS NULL OR LOWER(p.address) LIKE LOWER(CONCAT('%', :address, '%'))) AND " +
            "(:staffId IS NULL OR p.user.id = :staffId)")
    Page<Property> searchProperty(
            @Param("propertyId") Long propertyId,
            @Param("type") PropertyType type,
            @Param("minArea") Double minArea,
            @Param("maxArea") Double maxArea,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("status") PropertyStatus status,
            @Param("address") String address,
            @Param("staffId") Long staffId,
            Pageable pageable);
}

package com.example.springboot.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "provinces")
public class ProvinceEntity {
    @Id
    @Column(name = "code")
    private String code;
    @Column(name = "name", nullable = false)
    private String name;
    @OneToMany(mappedBy = "province", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<DistrictEntity> districts = new ArrayList<>();

    // Getters and Setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<DistrictEntity> getDistricts() { return districts; }
    public void setDistricts(List<DistrictEntity> districts) { this.districts = districts; }
}

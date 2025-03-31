package com.example.springboot.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "districts")
public class DistrictEntity {
    @Id
    @Column(name = "code")
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_code")
    @JsonIgnore
    private ProvinceEntity province;

    @OneToMany(mappedBy = "district", fetch = FetchType.LAZY)
    private List<WardEntity> wards = new ArrayList<>();

    // Getters and Setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public ProvinceEntity getProvince() { return province; }
    public void setProvince(ProvinceEntity province) { this.province = province; }
    public List<WardEntity> getWards() { return wards; }
    public void setWards(List<WardEntity> wards) { this.wards = wards; }
}

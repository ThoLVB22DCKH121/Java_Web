package com.example.springboot.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "wards")
public class WardEntity {
    @Id
    @Column(name = "code")
    private String code;
    @Column(name = "name", nullable = false)
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_code")
    @JsonIgnore
    private DistrictEntity district;
    @OneToMany(mappedBy = "ward")
    private List<ProjectEntity> projects = new ArrayList<>();


    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public DistrictEntity getDistrict() {
        return district;
    }
    public void setDistrict(DistrictEntity district) {
        this.district = district;
    }
    public List<ProjectEntity> getProjects() {
        return projects;
    }
    public void setProjects(List<ProjectEntity> projects) {
        this.projects = projects;
    }
}
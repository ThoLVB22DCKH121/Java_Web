package com.example.springboot.repository.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "utility")
public class UtilityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "icon")
    private String icon;
    @ManyToMany(mappedBy = "utilities")
    private List<ProjectEntity> projects = new ArrayList<>();

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public List<ProjectEntity> getProjects() {
        return projects;
    }
    public void setProjects(List<ProjectEntity> projects) {
        this.projects = projects;
    }
}

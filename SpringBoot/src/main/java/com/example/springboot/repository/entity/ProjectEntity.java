package com.example.springboot.repository.entity;

import com.example.springboot.enums.ProjectStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects")
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;
    @Column(name = "investor_name")
    private String investorName;
    @Column(name = "investor_address")
    private String investorAddress;
    @Column(name = "investor_phone")
    private String investorPhone;
    @Column(name = "investor_email")
    private String investorEmail;
    @Column(name = "likeproject")
    private Long likeProject;

    @ManyToOne
    @JoinColumn(name = "ward_code")
    @JsonIgnore
    private WardEntity ward;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PropertyEntity> properties;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProjectImageEntity> images;

    @ManyToMany
    @JoinTable(
            name = "project_utility",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "utility_id")
    )
    private List<UtilityEntity> utilities = new ArrayList<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    private String createdBy;

    public String getAddress(){
        return String.format("%s, %s, %s", ward.getName(), ward.getDistrict().getName(), ward.getDistrict().getProvince().getName());
    }

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
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public ProjectStatus getStatus() {
        return status;
    }
    public void setStatus(ProjectStatus status) {
        this.status = status;
    }
    public String getInvestorName() {
        return investorName;
    }
    public void setInvestorName(String investorName) {
        this.investorName = investorName;
    }
    public String getInvestorAddress() {
        return investorAddress;
    }
    public void setInvestorAddress(String investorAddress) {
        this.investorAddress = investorAddress;
    }
    public String getInvestorPhone() {
        return investorPhone;
    }
    public void setInvestorPhone(String investorPhone) {
        this.investorPhone = investorPhone;
    }
    public String getInvestorEmail() {
        return investorEmail;
    }
    public void setInvestorEmail(String investorEmail) {
        this.investorEmail = investorEmail;
    }
    public List<PropertyEntity> getProperties() {
        return properties;
    }
    public void setProperties(List<PropertyEntity> properties) {
        this.properties = properties;
    }
    public List<ProjectImageEntity> getImages() {
        return images;
    }
    public void setImages(List<ProjectImageEntity> images) {
        this.images = images;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    public Long getLikeProject() {
        return likeProject;
    }
    public void setLikeProject(Long likeProject) {
        this.likeProject = likeProject;
    }
    public String getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    public List<UtilityEntity> getUtilities() {
        return utilities;
    }
    public void setUtilities(List<UtilityEntity> utilities) {
        this.utilities = utilities;
    }
    public WardEntity getWard() {
        return ward;
    }
    public void setWard(WardEntity ward) {
        this.ward = ward;
    }
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
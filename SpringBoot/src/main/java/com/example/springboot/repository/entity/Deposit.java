package com.example.springboot.repository.entity;

import com.example.springboot.enums.DepositStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "deposit")
public class Deposit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "note")
    private String note;
    @Column(name = "depositAmount")
    private Long depositAmount;
    @Column(name = "create_at")
    private Date createAt;
    @Column(name = "status")
    private DepositStatus status;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public Property getProperty() {
        return property;
    }
    public void setProperty(Property property) {
        this.property = property;
    }
    public Long getDepositAmount() {
        return depositAmount;
    }
    public void setDepositAmount(Long depositAmount) {
        this.depositAmount = depositAmount;
    }
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
    public Date getCreateAt() {
        return createAt;
    }
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
    public DepositStatus getStatus() {
        return status;
    }
    public void setStatus(DepositStatus status) {
        this.status = status;
    }
}

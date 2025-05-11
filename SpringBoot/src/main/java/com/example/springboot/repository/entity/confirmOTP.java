package com.example.springboot.repository.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "confirm_otp")
public class confirmOTP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String otp;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private Date createAt;

    public confirmOTP() {}

    public confirmOTP(String otp, User user, Date createAt) {
        this.otp = otp;
        this.user = user;
        this.createAt = createAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getOtp() { return otp; }
    public void setOtp(String otp) { this.otp = otp; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Date getCreateAt() { return createAt; }
    public void setCreateAt(Date createAt) { this.createAt = createAt; }
}
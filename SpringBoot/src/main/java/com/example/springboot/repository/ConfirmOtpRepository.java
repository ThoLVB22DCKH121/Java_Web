package com.example.springboot.repository;

import com.example.springboot.repository.entity.confirmOTP;
import com.example.springboot.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmOtpRepository extends JpaRepository<confirmOTP, Long> {
    confirmOTP findByOtp(String otp);
    confirmOTP findByUser(User user);
}


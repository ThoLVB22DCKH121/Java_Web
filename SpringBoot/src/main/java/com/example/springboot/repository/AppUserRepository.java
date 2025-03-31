package com.example.springboot.repository;

import com.example.springboot.enums.UserRole;
import com.example.springboot.repository.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
    public AppUser findByUsername(String username);
}

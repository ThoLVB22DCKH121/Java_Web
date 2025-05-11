package com.example.springboot.repository;

import com.example.springboot.repository.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUsername(String username);
    public Optional<User> findByEmail(String email);
    @Query("SELECT u FROM User u WHERE " +
            "(:keyword IS NULL OR LOWER(u.fullname) LIKE LOWER(CONCAT('%', :keyword, '%'))) OR " +
            "(:keyword IS NULL OR LOWER(u.role) LIKE LOWER(CONCAT('%', :keyword, '%'))) OR " +
            "(:keyword IS NULL OR LOWER(u.address) LIKE LOWER(CONCAT('%', :keyword, '%'))) OR " +
            "(:keyword IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%',:keyword, '%'))) OR " +
            "(:keyword IS NULL OR LOWER(u.phone) LIKE LOWER(CONCAT('%',:keyword, '%')))")
    public Page<User> searchUser(
            @Param("keyword") String keyword,
            Pageable pageable
    );
}

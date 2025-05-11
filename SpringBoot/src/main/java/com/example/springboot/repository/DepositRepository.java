package com.example.springboot.repository;

import com.example.springboot.repository.entity.Deposit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DepositRepository extends JpaRepository<Deposit, Long> {
    @Query("SELECT d FROM Deposit d JOIN d.user u WHERE " +
            "(:keyword IS NULL OR LOWER(d.note) LIKE LOWER(CONCAT('%', :keyword, '%'))) OR " +
            "(:keyword IS NULL OR LOWER(u.fullname) LIKE LOWER(CONCAT('%', :keyword, '%'))) OR " +
            "(:keyword IS NULL OR LOWER(u.phone) LIKE LOWER(CONCAT('%', :keyword, '%'))) OR " +
            "(:keyword IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Deposit> searchDeposits(@Param("keyword") String keyword, Pageable pageable);

    Page<Deposit> findByUserId(Long userId, Pageable pageable);
}

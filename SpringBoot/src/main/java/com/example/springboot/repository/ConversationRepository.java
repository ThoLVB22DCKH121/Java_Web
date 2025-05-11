package com.example.springboot.repository;

import com.example.springboot.repository.entity.Conversation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    @Query("SELECT c FROM Conversation c JOIN c.users u1 JOIN c.users u2 JOIN c.property p " +
            "WHERE u1.id = :userId1 AND u2.id = :userId2 AND u1.id != u2.id AND p.id = :propertyId")
    Optional<Conversation> findByTwoUsersAndProperty(Long userId1, Long userId2, Long propertyId);

    Page<Conversation> findByUsersId(Long userId, Pageable pageable);
}

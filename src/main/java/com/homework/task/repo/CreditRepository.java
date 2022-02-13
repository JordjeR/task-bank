package com.homework.task.repo;

import com.homework.task.entities.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreditRepository extends JpaRepository<Credit, Long> {
    Optional<Credit> findById(Long id);
}
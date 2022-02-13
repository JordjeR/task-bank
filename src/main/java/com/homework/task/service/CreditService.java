package com.homework.task.service;

import com.homework.task.entities.Credit;

import java.util.List;
import java.util.Optional;

public interface CreditService {
    Optional<Credit> findById(Long id);

    List<Credit> findAll();

    void delete(Credit credit);

    void save(Credit credit);
}
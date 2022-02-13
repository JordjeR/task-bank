package com.homework.task.service;

import com.homework.task.entities.Bank;

import java.util.List;

public interface BankService {
    List<Bank> findAll();

    void delete(Bank bank);

    void save(Bank bank);
}
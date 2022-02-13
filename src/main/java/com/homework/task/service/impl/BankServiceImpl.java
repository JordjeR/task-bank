package com.homework.task.service.impl;

import com.homework.task.entities.Bank;
import com.homework.task.repo.BankRepository;
import com.homework.task.service.BankService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;

    public BankServiceImpl(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public List<Bank> findAll() {
        return bankRepository.findAll();
    }

    @Override
    public void delete(Bank bank) {
        bankRepository.delete(bank);
    }

    @Override
    public void save(Bank bank) {
        bankRepository.save(bank);
    }
}

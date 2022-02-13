package com.homework.task.service.impl;

import com.homework.task.entities.Credit;
import com.homework.task.repo.CreditRepository;
import com.homework.task.service.CreditService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CreditServiceImpl implements CreditService {

    private final CreditRepository creditRepository;

    public CreditServiceImpl(CreditRepository creditService) {
        this.creditRepository = creditService;
    }

    @Override
    public List<Credit> findAll() {
        return creditRepository.findAll();
    }

    @Override
    public Optional<Credit> findById(Long id) {
        return creditRepository.findById(id);
    }

    @Override
    public void delete(Credit credit) {
        creditRepository.delete(credit);
    }

    @Override
    public void save(Credit credit) {
        creditRepository.save(credit);
    }
}

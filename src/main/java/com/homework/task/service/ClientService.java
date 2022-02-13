package com.homework.task.service;

import com.homework.task.entities.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    List<Client> findAll();

    List<Client> findAll(String filter);

    Optional<Client> findById(Long id);

    void delete(Client client);

    void save(Client client);
}
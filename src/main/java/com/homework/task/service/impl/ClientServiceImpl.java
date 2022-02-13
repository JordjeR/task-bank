package com.homework.task.service.impl;

import com.homework.task.entities.Client;
import com.homework.task.repo.ClientRepository;
import com.homework.task.service.ClientService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public List<Client> findAll(String filter) {
        return clientRepository.findByNameStartingWithIgnoreCase(filter);
    }

    @Override
    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public void delete(Client client) {
        clientRepository.delete(client);
    }

    @Override
    public void save(Client client) {
        clientRepository.save(client);
    }
}

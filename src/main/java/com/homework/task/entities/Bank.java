package com.homework.task.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "bank")
public class Bank {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "bank", fetch = FetchType.EAGER)
    private List<Client> clients;

    @OneToMany(mappedBy = "bank", fetch = FetchType.EAGER)
    private List<Credit> credits;

    public Bank() {
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public List<Credit> getCredits() {
        return credits;
    }

    public void setCredits(List<Credit> credits) {
        this.credits = credits;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

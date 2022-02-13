package com.homework.task.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "client")
public class Client {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "number")
    private String number;

    @Column(name = "email")
    private String email;

    @Column(name = "passport")
    private String passport;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "bank_id")
    private Bank bank;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<CreditOffer> offers;

    public Client() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Bank getBank() {
        return bank;
    }

    public List<CreditOffer> getOffers() {
        return offers;
    }

    public void setOffers(List<CreditOffer> offers) {
        this.offers = offers;
    }

    @Override
    public String toString() {
        return name + " " + surname + " " + patronymic;
    }
}

package com.homework.task.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "credit")
public class Credit {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "creditLimit")
    private Double creditLimit;

    @Column(name = "interestRate")
    private Double interestRate;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "bank_id")
    private Bank bank;

    @OneToMany(mappedBy = "credit", fetch = FetchType.EAGER)
    private List<CreditOffer> offers;

    public Credit() {
    }

    public Double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return creditLimit + " : " + interestRate + "%";
    }
}

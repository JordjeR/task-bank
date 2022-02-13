package com.homework.task.entities;

import javax.persistence.*;

@Entity
@Table(name = "credit_offer")
public class CreditOffer extends PaymentSchedule {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "credit_id")
    private Credit credit;

    @Column(name = "amountOfCredit")
    private Double amountOfCredit;

    public CreditOffer() {
    }


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }

    public Double getAmountOfCredit() {
        return amountOfCredit;
    }

    public void setAmountOfCredit(Double amountOfCredit) {
        this.amountOfCredit = amountOfCredit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CreditOffer{" +
                "client=" + client +
                ", credit=" + credit +
                ", amountOfCredit=" + amountOfCredit +
                '}';
    }
}

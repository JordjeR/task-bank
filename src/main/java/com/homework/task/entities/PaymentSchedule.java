package com.homework.task.entities;

import javax.persistence.*;
import java.time.LocalDate;

@MappedSuperclass
public abstract class PaymentSchedule {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "datePayment")
    private LocalDate datePayment;

    @Column(name = "sumPayment")
    private Double sumPayment;

    @Column(name = "sumRepayCredit")
    private Double sumRepayCredit;

    @Column(name = "sumRepayPercent")
    private Double sumRepayPercent;

    public PaymentSchedule() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatePayment() {
        return datePayment;
    }

    public void setDatePayment(LocalDate datePayment) {
        this.datePayment = datePayment;
    }

    public Double getSumPayment() {
        return sumPayment;
    }

    public void setSumPayment(Double sumPayment) {
        this.sumPayment = sumPayment;
    }

    public Double getSumRepayCredit() {
        return sumRepayCredit;
    }

    public void setSumRepayCredit(Double sumRepayCredit) {
        this.sumRepayCredit = sumRepayCredit;
    }

    public Double getSumRepayPercent() {
        return sumRepayPercent;
    }

    public void setSumRepayPercent(Double sumRepayPercent) {
        this.sumRepayPercent = sumRepayPercent;
    }
}
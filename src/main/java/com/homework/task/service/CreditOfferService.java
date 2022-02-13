package com.homework.task.service;

import com.homework.task.entities.CreditOffer;

import java.util.List;

public interface CreditOfferService {
    List<CreditOffer> calculatePaymentSchedule(CreditOffer creditOffer, Double months);

    CreditOffer getPayment(Double sum, Double debt, Double monthlyInterestRate);

    Double getMonthlyPayment(CreditOffer creditOffer, Double month);

    Double getInterestRate(Double month);

    List<CreditOffer> findAll();

    void delete(CreditOffer creditOffer);

    void save(CreditOffer creditOffer);
}
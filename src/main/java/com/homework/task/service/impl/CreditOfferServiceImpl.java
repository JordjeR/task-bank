package com.homework.task.service.impl;

import com.homework.task.entities.CreditOffer;
import com.homework.task.repo.CreditOfferRepository;
import com.homework.task.service.CreditOfferService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreditOfferServiceImpl implements CreditOfferService {

    private final CreditOfferRepository creditOfferRepository;

    public CreditOfferServiceImpl(CreditOfferRepository creditOfferRepository) {
        this.creditOfferRepository = creditOfferRepository;
    }

    @Override
    public List<CreditOffer> calculatePaymentSchedule(CreditOffer creditOffer, Double months) {
        List<CreditOffer> creditOffers = new ArrayList<>();

        Double monthlyPaymentAmount = getMonthlyPayment(creditOffer, months);
        Double amountOfCredit = creditOffer.getAmountOfCredit();
        Double monthlyInterestRate = getInterestRate(creditOffer.getCredit().getInterestRate());

        CreditOffer offer;

        for (int i = 0; i < months.intValue(); i++) {
            offer = getPayment(monthlyPaymentAmount, amountOfCredit, monthlyInterestRate);
            creditOffers.add(offer);
            amountOfCredit -= offer.getSumRepayCredit();
        }

        return creditOffers;
    }

    @Override
    public CreditOffer getPayment(Double sum, Double debt, Double monthlyInterestRate) {
        Double sumOfRepaymentForCreditPercents = debt * monthlyInterestRate;

        CreditOffer offer = new CreditOffer();

        offer.setSumPayment(sum);
        offer.setSumRepayCredit(sum - sumOfRepaymentForCreditPercents);
        offer.setSumRepayPercent(sumOfRepaymentForCreditPercents);

        return offer;
    }

    @Override
    public Double getMonthlyPayment(CreditOffer creditOffer, Double month) {
        Double credit = creditOffer.getAmountOfCredit();
        Double percent = creditOffer.getCredit().getInterestRate();

        final double p = getInterestRate(percent);

        return credit * (p + (p / (Math.pow((1 + p), month.intValue()) - 1)));
    }

    @Override
    public Double getInterestRate(Double interestRate) {
        return (interestRate / 100 / 12);
    }

    @Override
    public List<CreditOffer> findAll() {
        return creditOfferRepository.findAll();
    }

    @Override
    public void delete(CreditOffer creditOffer) {
        creditOfferRepository.delete(creditOffer);
    }

    @Override
    public void save(CreditOffer creditOffer) {
        creditOfferRepository.save(creditOffer);
    }
}
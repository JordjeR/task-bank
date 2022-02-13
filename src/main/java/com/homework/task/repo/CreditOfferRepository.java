package com.homework.task.repo;

import com.homework.task.entities.CreditOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditOfferRepository extends JpaRepository<CreditOffer, Long> {
}
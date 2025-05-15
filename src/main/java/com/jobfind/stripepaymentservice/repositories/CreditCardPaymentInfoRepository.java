package com.jobfind.stripepaymentservice.repositories;

import com.jobfind.stripepaymentservice.models.CreditCardPaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardPaymentInfoRepository extends JpaRepository<CreditCardPaymentInfo, Integer> {
}
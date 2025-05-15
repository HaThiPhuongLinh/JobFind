package com.jobfind.stripepaymentservice.repositories;

import com.jobfind.stripepaymentservice.models.SubscriptionPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan, Integer> {
}
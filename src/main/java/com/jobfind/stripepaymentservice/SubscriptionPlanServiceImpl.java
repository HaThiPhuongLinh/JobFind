package com.jobfind.stripepaymentservice;

import com.jobfind.stripepaymentservice.models.SubscriptionPlan;
import com.jobfind.stripepaymentservice.repositories.SubscriptionPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionPlanServiceImpl implements ISubscriptionPlanService{
    private final SubscriptionPlanRepository subscriptionPlanRepository;

    @Override
    public SubscriptionPlan getSubscriptionPlan(Integer planId) {
        return subscriptionPlanRepository.findById(planId)
                .orElseThrow(() -> new RuntimeException("Subscription plan not found"));
    }

    @Override
    public List<SubscriptionPlan> listAllSubscriptionPlans() {
        return subscriptionPlanRepository.findAll();
    }
}

package com.jobfind.stripepaymentservice;

import com.jobfind.stripepaymentservice.models.SubscriptionPlan;

import java.util.List;

public interface ISubscriptionPlanService {
    SubscriptionPlan getSubscriptionPlan(Integer planId);

    List<SubscriptionPlan> listAllSubscriptionPlans();
}

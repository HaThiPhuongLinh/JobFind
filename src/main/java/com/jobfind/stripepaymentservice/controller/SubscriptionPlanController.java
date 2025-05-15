package com.jobfind.stripepaymentservice.controller;

import com.jobfind.stripepaymentservice.ISubscriptionPlanService;
import com.jobfind.stripepaymentservice.models.SubscriptionPlan;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/plan")
@RequiredArgsConstructor
public class SubscriptionPlanController {
     private final ISubscriptionPlanService subscriptionPlanServiceImpl;

     @GetMapping("/{planId}")
     public ResponseEntity<SubscriptionPlan> getSubscriptionPlan(@PathVariable Integer planId) {
         SubscriptionPlan subscriptionPlan = subscriptionPlanServiceImpl.getSubscriptionPlan(planId);
         return ResponseEntity.ok(subscriptionPlan);
     }

     @GetMapping("/list")
     public ResponseEntity<List<SubscriptionPlan>> listAllSubscriptionPlans() {
         List<SubscriptionPlan> subscriptionPlans = subscriptionPlanServiceImpl.listAllSubscriptionPlans();
         return ResponseEntity.ok(subscriptionPlans);
     }
}

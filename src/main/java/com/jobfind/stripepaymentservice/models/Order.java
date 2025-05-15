package com.jobfind.stripepaymentservice.models;

import com.jobfind.models.User;
import com.jobfind.stripepaymentservice.models.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Double paymentServiceFees;
    private Double totalPriceLessFees;
    private Double totalPrice;

    private PaymentStatus status;

    private LocalDateTime createdAt;
    private boolean isPay;

    @ManyToOne
    @JoinColumn(name = "credit_card_payment_info_id")
    private CreditCardPaymentInfo creditCardPaymentInfo;

    @ManyToOne
    @JoinColumn(name = "subscription_plan_id", nullable = false)
    private SubscriptionPlan subscriptionPlan;
    private String paymentIntentId;
}

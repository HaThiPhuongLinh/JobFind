package com.jobfind.stripepaymentservice;

import com.jobfind.stripepaymentservice.models.Order;
import com.jobfind.stripepaymentservice.request.OrderRequest;
import com.jobfind.stripepaymentservice.response.OrderResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public interface IPaymentService {
    void updatePaymentIntentForOrderFirst(Order order) throws StripeException;
    String updatePaymentIntentForOrderSecond(Order order) throws StripeException;
    void updatePaymentInfoForOrder(Order order) throws StripeException;
    void chargeForOrder(Order order) throws StripeException;
    PaymentIntent getPaymentIntent(Order order) throws StripeException;
    void cancelPaymentIntent(Order order) throws StripeException;
    OrderResponse createCardPaymentForOrder(Order order) throws Exception;
    String createPaymentIntent(Order order) throws StripeException;
    boolean isIntentUnCapture(Order order) throws StripeException;
    OrderResponse createOrder(OrderRequest orderRequest) throws Exception;
    OrderResponse chargeOrder(Integer orderId) throws Exception;
    OrderResponse getOrderByUserId(Integer userId) throws Exception;
    OrderResponse changeSubscriptionPlan(Integer userId, Integer newPlanId) throws Exception;
}

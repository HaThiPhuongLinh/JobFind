package com.jobfind.stripepaymentservice.repositories;

import com.jobfind.stripepaymentservice.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order getOrderByOrderId(Integer orderId);
    Order getOrderByUserUserId(Integer userId);
}
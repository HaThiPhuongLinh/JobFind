package com.jobfind.stripepaymentservice.repositories;

import com.jobfind.stripepaymentservice.models.Order;
import com.jobfind.stripepaymentservice.models.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order getOrderByOrderId(Integer orderId);
    Order getOrderByUserUserId(Integer userId);

    Optional<Order> findByUserUserIdAndStatus(Integer userId, PaymentStatus status);

    @Query("SELECT o FROM Order o WHERE o.user.userId = :userId ORDER BY o.createdAt DESC")
    List<Order> findLatestByUserId(@Param("userId") Integer userId);
}
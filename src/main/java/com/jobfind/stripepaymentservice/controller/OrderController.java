package com.jobfind.stripepaymentservice.controller;
import com.jobfind.stripepaymentservice.IPaymentService;
import com.jobfind.stripepaymentservice.request.OrderRequest;
import com.jobfind.stripepaymentservice.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final IPaymentService paymentServiceImpl;

    @PostMapping("/create")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) throws Exception {
        OrderResponse orderResponse = paymentServiceImpl.createOrder(orderRequest);
        return ResponseEntity.ok(orderResponse);
    }

    @PutMapping("/charge/{orderId}")
    public ResponseEntity<OrderResponse> chargeOrder(@PathVariable Integer orderId) throws Exception {
        OrderResponse orderResponse = paymentServiceImpl.chargeOrder(orderId);
        return ResponseEntity.ok(orderResponse);
    }

    @PutMapping("/change-plan/{userId}/{newPlanId}")
    public ResponseEntity<OrderResponse> changeSubscriptionPlan(@PathVariable Integer userId, @PathVariable Integer newPlanId) throws Exception {
        OrderResponse orderResponse = paymentServiceImpl.changeSubscriptionPlan(userId, newPlanId);
        return ResponseEntity.ok(orderResponse);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<OrderResponse> getOrderByUserId(@PathVariable Integer userId) throws Exception {
        OrderResponse orderResponse = paymentServiceImpl.getOrderByUserId(userId);
        return ResponseEntity.ok(orderResponse);
    }
}

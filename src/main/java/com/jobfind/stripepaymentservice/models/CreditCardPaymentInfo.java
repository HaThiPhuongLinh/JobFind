package com.jobfind.stripepaymentservice.models;

import com.jobfind.models.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreditCardPaymentInfo {
    @Id
    private String id;
    private String number;
    private String type;
    private String validToMonth;
    private String validToYear;
    private String subscriptionId;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}

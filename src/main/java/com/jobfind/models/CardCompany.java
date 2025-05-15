package com.jobfind.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CardCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String number;
    private String type;
    private String validToMonth;
    private String validToYear;
    private String ccv;
    private String postalCode;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}

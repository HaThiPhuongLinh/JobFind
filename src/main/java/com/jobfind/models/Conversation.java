package com.jobfind.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "job_seeker_user_id")
    private User jobSeeker;
    @ManyToOne
    @JoinColumn(name = "employer_user_id")
    private User company;
    private Integer unreadCountJobSeeker = 0;
    private Integer unreadCountCompany = 0;
    private LocalDateTime createAt;
    private LocalDateTime lastMessageAt;
}

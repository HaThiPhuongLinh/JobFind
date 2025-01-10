package jobfind.com.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer companyId;

    private String companyName;
    private String logoPath;
    private String industry;
    private String website;
    private String description;

    @Column(columnDefinition = "boolean default false")
    private Boolean isVerified;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
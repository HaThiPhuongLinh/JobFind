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
public class JobSeekerProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer profileId;

    private String firstName;

    private String lastName;

    private String phone;

    private String resumePath;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
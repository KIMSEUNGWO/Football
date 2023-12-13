package football.start.allOfFootball.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "BEFORE_PASSWORD")
@SequenceGenerator(name = "SEQ_BEFORE_PASSWORD", sequenceName = "SEQ_BEFORE_PASSWORD_ID", allocationSize = 1)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeforePassword {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BEFORE_PASSWORD")
    private Long beforePasswordId;

    @OneToOne
    @JoinColumn(name = "memberId")
    private Member member;

    private String beforePassword;
    private LocalDateTime passwordChangeDate;


}

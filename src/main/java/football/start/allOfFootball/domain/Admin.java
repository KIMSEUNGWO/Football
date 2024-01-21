package football.start.allOfFootball.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "ADMIN")
@SequenceGenerator(name = "SEQ_ADMIN", sequenceName = "SEQ_ADMIN_ID", allocationSize = 1)
public class Admin {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ADMIN")
    private Long adminId;

    @OneToOne
    @JoinColumn(name = "memberId")
    private Member member;
}

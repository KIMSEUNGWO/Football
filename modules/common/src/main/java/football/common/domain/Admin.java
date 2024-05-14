package football.common.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "ADMIN")
@SequenceGenerator(name = "SEQ_ADMIN", sequenceName = "SEQ_ADMIN_ID", allocationSize = 1)
public class Admin {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ADMIN")
    private Long adminId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;
}

package football.common.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "ADMIN")
public class Admin {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;
}

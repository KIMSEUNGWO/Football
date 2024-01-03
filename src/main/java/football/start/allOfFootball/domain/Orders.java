package football.start.allOfFootball.domain;

import football.start.allOfFootball.enums.TeamEnum;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "ORDERS")
@SequenceGenerator(name = "SEQ_ORDERS", sequenceName = "SEQ_ORDERS_ID", allocationSize = 1)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ORDERS")
    private Long ordersId;

    @ManyToOne
    @JoinColumn(name = "matchId")
    private Match match;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    private Integer payment; // 결제한 금액

    @Enumerated(EnumType.STRING)
    private TeamEnum team;

    @OneToOne
    @JoinColumn(name = "matchCancelId")
    private MatchCancel matchCancel;

    @OneToOne
    @JoinColumn(name = "couponListId")
    private CouponList couponList;

    private Integer score;

    public static Orders build(Match match, Member member) {
        return Orders.builder()
            .match(match)
            .member(member)
            .build();
    }

}

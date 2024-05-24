package football.common.domain;

import football.common.enums.domainenum.TeamEnum;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "ORDERS")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Orders {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ordersId;

    @ManyToOne
    @JoinColumn(name = "MATCH_ID")
    private Match match;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private Integer amountPayment; // 결제한 금액

    @Enumerated(EnumType.STRING)
    private TeamEnum team;

    @OneToOne
    @JoinColumn(name = "MATCH_CANCEL_ID")
    private MatchCancel matchCancel;

    @OneToOne
    @JoinColumn(name = "COUPON_LIST_ID")
    private CouponList couponList;

    private Integer score;
}

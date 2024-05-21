package football.common.domain;

import football.common.enums.paymentEnums.CashEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "CASH")
public class Cash extends BaseTimeEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cashId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @Enumerated(EnumType.STRING)
    private CashEnum cashTitle;

    private Integer cashUse; // 캐시 사용금액
    private Integer cashNow; // 캐시 잔액
    

}

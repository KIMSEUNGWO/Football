package football.internal.database.domain;

import football.start.allOfFootball.enums.paymentEnums.CashEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "CASH")
@SequenceGenerator(name = "SEQ_CASH", sequenceName = "SEQ_CASH_ID", allocationSize = 1)
public class Cash extends BaseTimeEntity{

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CASH")
    private Long cashId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @Enumerated(EnumType.STRING)
    private CashEnum cashTitle;

    private Integer cashUse; // 캐시 사용금액
    private Integer cashNow; // 캐시 잔액
    

}

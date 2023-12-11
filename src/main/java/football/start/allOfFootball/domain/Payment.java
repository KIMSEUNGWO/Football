package football.start.allOfFootball.domain;

import football.start.allOfFootball.enums.paymentEnums.CashEnum;
import football.start.allOfFootball.enums.paymentEnums.PaymentType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@Entity
@Table(name = "PAYMENT")
@SequenceGenerator(name = "SEQ_PAYMENT", sequenceName = "SEQ_PAYMENT_ID", allocationSize = 1)
public class Payment extends BaseTimeEntity{

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PAYMENT")
    private Long paymentId;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    private Integer charge;
    private Integer resultCash;

    @Enumerated(EnumType.STRING)
    private CashEnum cashType; // 용도

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType; // 충전방식


}

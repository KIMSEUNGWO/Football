package football.common.domain;

import football.common.enums.paymentEnums.CashEnum;
import football.common.enums.paymentEnums.PaymentType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@ToString
@Entity
@Table(name = "PAYMENT")
@NoArgsConstructor
@AllArgsConstructor
public class Payment extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private Integer charge;
    private Integer resultCash;

    @Enumerated(EnumType.STRING)
    private CashEnum cashType; // 용도

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType; // 충전방식


}

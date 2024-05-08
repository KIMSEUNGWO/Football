package football.start.allOfFootball.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter // 잠시 테스트용
@Entity
@Table(name = "COUPON")
@SequenceGenerator(name = "SEQ_COUPON", sequenceName = "SEQ_COUPON_ID", allocationSize = 1)
public class Coupon {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COUPON")
    private Long couponId;

    private String couponName;
    private int couponDiscount;
    private int couponLimitDay;
}

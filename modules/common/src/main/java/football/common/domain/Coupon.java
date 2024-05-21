package football.common.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter // 잠시 테스트용
@Entity
@Table(name = "COUPON")
public class Coupon {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponId;

    private String couponName;
    private int couponDiscount;
    private int couponLimitDay;
}

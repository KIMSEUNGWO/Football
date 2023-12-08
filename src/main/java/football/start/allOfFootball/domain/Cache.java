package football.start.allOfFootball.domain;

import football.start.allOfFootball.enums.paymentEnums.CacheEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "CACHE")
@SequenceGenerator(name = "SEQ_CACHE", sequenceName = "SEQ_CACHE_ID", allocationSize = 1)
public class Cache extends BaseTimeEntity{

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CACHE")
    private Long cacheId;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @Enumerated(EnumType.STRING)
    private CacheEnum cacheTitle;

    private Integer cacheUse; // 캐시 사용금액
    private Integer cacheNow; // 캐시 잔액
    

}

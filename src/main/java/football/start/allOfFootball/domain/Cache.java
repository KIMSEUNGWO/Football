package football.start.allOfFootball.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "CACHE")
@SequenceGenerator(name = "SEQ_CACHE", sequenceName = "SEQ_CHAHE_ID", allocationSize = 1)
public class Cache extends BaseTimeEntity{

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CACHE")
    private Long cacheId;
    private Long memberId;
    private Long playId;

    private int cacheUse; // 캐시 사용금액
    private int cacheNow; // 캐시 잔액

}

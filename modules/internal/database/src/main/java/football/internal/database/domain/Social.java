package football.internal.database.domain;

import football.start.allOfFootball.enums.SocialEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Builder
@Getter
@Entity
@Table(name = "SOCIAL")
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@SequenceGenerator(name = "SEQ_SOCIAL", sequenceName = "SEQ_SOCIAL_ID", allocationSize = 1)
public class Social {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SOCIAL")
    private Long socialId;


    @OneToOne
    @JoinColumn(name = "memberId")
    private Member member;

    private Integer socialNum; // 고유번호

    @Enumerated(EnumType.STRING)
    private SocialEnum socialType;

    @OneToOne(mappedBy = "social")
    private KakaoToken kakaoToken;
}

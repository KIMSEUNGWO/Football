package football.common.domain;

import football.common.enums.SocialEnum;
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
public class Social {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long socialId;

    @OneToOne
    @JoinColumn(name = "memberId")
    private Member member;

    private int socialNum; // 고유번호

    @Enumerated(EnumType.STRING)
    private SocialEnum socialType;

    @OneToOne(mappedBy = "social")
    private KakaoToken kakaoToken;

    public boolean isType(SocialEnum socialEnum) {
        return socialType == socialEnum;
    }

    public boolean isCorrect(int socialNum) {
        return this.socialNum == socialNum;
    }
}

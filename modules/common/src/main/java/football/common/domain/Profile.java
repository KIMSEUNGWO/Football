package football.common.domain;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "PROFILE")
@SequenceGenerator(name = "SEQ_PROFILE", sequenceName = "SEQ_PROFILE_ID", allocationSize = 1)
public class Profile extends ImageChild {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PROFILE")
    private Long profileId;

    @OneToOne
    @JoinColumn(name = "memberId")
    private Member member;

    public Profile(Member member, String originalName, String storeName) {
        super(originalName, storeName);
        this.member = member;
    }
}

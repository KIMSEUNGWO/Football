package football.common.domain;

import football.common.enums.domainenum.LocationEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Manager {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long managerId;

    @Getter
    @OneToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private String name; // 본명
    @Enumerated(EnumType.STRING)
    private LocationEnum region; // 활동구역

    // Not Columns
    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
    private List<Match> matchList;

    public boolean isSameMember(Member compareMember) {
        if (member == null || compareMember == null) return false;
        return member.equals(compareMember);
    }
}

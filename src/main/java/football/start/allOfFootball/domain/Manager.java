package football.start.allOfFootball.domain;

import football.start.allOfFootball.enums.LocationEnum;
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
@SequenceGenerator(name = "SEQ_MANAGER", sequenceName = "SEQ_MANAGER_ID", allocationSize = 1)
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MANAGER")
    private Long managerId;

    @Getter
    @OneToOne
    @JoinColumn(name = "memberId")
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

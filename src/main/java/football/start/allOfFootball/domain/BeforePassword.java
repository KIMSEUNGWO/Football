package football.start.allOfFootball.domain;

import football.start.allOfFootball.formatter.DateFormatter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "BEFORE_PASSWORD")
@SequenceGenerator(name = "SEQ_BEFORE_PASSWORD", sequenceName = "SEQ_BEFORE_PASSWORD_ID", allocationSize = 1)
@NoArgsConstructor
public class BeforePassword {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BEFORE_PASSWORD")
    private Long beforePasswordId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @Getter
    private String beforePassword;
    private LocalDateTime passwordChangeDate;

    public void changeBeforePassword(String beforePassword) {
        this.beforePassword = beforePassword;
        this.passwordChangeDate = LocalDateTime.now();
    }

    public BeforePassword(Member member) {
        this.member = member;
        changeBeforePassword(member.getMemberPassword());
    }

    public String getConvertChangeDate() {
        return DateFormatter.format("yyyy년 MM월 dd일", passwordChangeDate);
    }
}

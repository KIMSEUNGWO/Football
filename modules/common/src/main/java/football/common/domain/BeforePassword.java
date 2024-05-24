package football.common.domain;

import football.common.formatter.DateFormatter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "BEFORE_PASSWORD")
@NoArgsConstructor
public class BeforePassword {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long beforePasswordId;

    @OneToOne
    @JoinColumn(name = "MEMBER_ID")
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

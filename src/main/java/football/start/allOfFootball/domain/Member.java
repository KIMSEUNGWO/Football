package football.start.allOfFootball.domain;

import football.start.allOfFootball.dto.ImageParent;
import football.start.allOfFootball.enums.GenderEnum;
import football.start.allOfFootball.enums.gradeEnums.GradeEnum;
import football.start.allOfFootball.enums.SocialEnum;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Entity
@Table(name = "MEMBER")
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@SequenceGenerator(name = "SEQ_MEMBER", sequenceName = "SEQ_MEMBER_ID", allocationSize = 1)
public class Member extends ImageParent {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MEMBER")
    private Long memberId;
    private String memberEmail;
    private String memberPassword;
    private String memberSalt;
    private String memberName;

    @Enumerated(EnumType.STRING)
    private GenderEnum memberGender;
    private LocalDate memberBirthday;
    private String memberPhone;
    private int memberCash;

    @Column(nullable = false)
    @ColumnDefault("1000")
    private Integer memberScore;

    @Enumerated(EnumType.STRING)
    @Nullable
    private GradeEnum grade;

    private LocalDateTime memberRecentlyDate;
    private LocalDateTime memberExpireDate;

    @CreatedDate
    private LocalDateTime createDate;

    // Not Columns
    @OneToOne(mappedBy = "member")
    private Profile profile;
    @OneToMany(mappedBy = "member")
    private List<CouponList> couponList;
    @OneToMany(mappedBy = "member")
    private List<Orders> ordersList;
    @OneToOne(mappedBy = "member")
    private BeforePassword beforePassword;
    @OneToOne(mappedBy = "member")
    private Manager manager;
    @OneToOne(mappedBy = "member")
    private Social social;

    public String combineSalt(String password) {
        return memberSalt + password;
    }

    public static String createSalt() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().substring(0, 4);
    }

    public void setMemberSalt() {
        this.memberSalt = createSalt();
    }

    public void renewLoginTime(LocalDateTime now) {
        memberRecentlyDate = now;
    }

    public void addScore(int score) {
        memberScore += score;
    }

    public void setMemberCash(int resultCash) {
        memberCash = resultCash;
    }

    public void setMemberPassword(String changePassword) {
        memberPassword = changePassword;
    }
}

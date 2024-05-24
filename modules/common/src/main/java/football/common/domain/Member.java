package football.common.domain;

import football.common.enums.Role;
import football.common.enums.SocialEnum;
import football.common.enums.gradeEnums.GradeEnum;
import football.common.enums.matchenum.GenderEnum;
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
public class Member implements ImageParent {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    private String memberEmail;
    @Setter
    private String memberPassword;
    private String memberName;

    @Enumerated(EnumType.STRING)
    private GenderEnum memberGender;
    private LocalDate memberBirthday;
    @Setter
    private String memberPhone;
    @Setter
    private int memberCash;

    @Column(nullable = false)
    @ColumnDefault("1000")
    private Integer memberScore;

    @Enumerated(EnumType.STRING)
    private GradeEnum grade;

    @Setter
    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDateTime memberRecentlyDate;
    private LocalDateTime memberExpireDate;

    @CreatedDate
    private LocalDateTime createDate;

    // Not Columns
    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
    private Profile profile;
    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
    private BeforePassword beforePassword;
    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
    private Manager manager;
    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
    private Social social;
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<CouponList> couponList;
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Orders> ordersList;

    public void renewLoginTime() {
        memberRecentlyDate = LocalDateTime.now();
    }

    public void addScore(int score) {
        memberScore += score;
    }

    public boolean isSocial() {
        return social != null;
    }

    public SocialEnum getSocialType() {
        return isSocial() ? social.getSocialType() : null;
    }

    public boolean isManager() {
        return manager != null;
    }
}

package football.start.allOfFootball.domain;

import football.start.allOfFootball.enums.GenderEnum;
import football.start.allOfFootball.enums.gradeEnums.GradeEnum;
import football.start.allOfFootball.enums.SocialEnum;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Entity
@Table(name = "MEMBER")
@AllArgsConstructor
@DynamicInsert
@SequenceGenerator(name = "SEQ_MEMBER", sequenceName = "SEQ_MEMBER_ID", allocationSize = 1)
public class Member extends BaseTimeEntity {

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

    @OneToOne
    @JoinColumn(name = "profileId")
    private Profile profile;

    @Enumerated(EnumType.STRING)
    @Nullable
    private GradeEnum grade;

    private LocalDate memberRecentlyDate;
    private LocalDateTime memberExpireDate;
    @Enumerated(EnumType.STRING)
    private SocialEnum memberSocial;

    // not columns
    @OneToMany(mappedBy = "member")
    private List<CouponList> couponList;
    @OneToMany(mappedBy = "member")
    private List<Orders> ordersList;

    public Member() {

    }


    public String combineSalt(String password) {
        return memberSalt + password;
    }

    public static String createSalt() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().substring(0, 4);
    }


    public void setCouponList(List<CouponList> couponList) {
        this.couponList = couponList;
    }

    public void setMemberCash(int memberCash) {
        this.memberCash = memberCash;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public void setMemberPassword(String memberPassword) {
        this.memberPassword = memberPassword;
    }

    public void setMemberSalt() {
        this.memberSalt = createSalt();
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public void setMemberGender(GenderEnum memberGender) {
        this.memberGender = memberGender;
    }

    public void setMemberBirthday(LocalDate memberBirthday) {
        this.memberBirthday = memberBirthday;
    }

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
    }

    public void setMemberRecentlyDate(LocalDate memberRecentlyDate) {
        this.memberRecentlyDate = memberRecentlyDate;
    }

    public void setMemberSocial(SocialEnum memberSocial) {
        this.memberSocial = memberSocial;
    }
}

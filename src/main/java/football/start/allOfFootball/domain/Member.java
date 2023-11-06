package football.start.allOfFootball.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "MEMBER")
@SequenceGenerator(name = "SEQ_MEMBER", sequenceName = "SEQ_MEMBER_ID", allocationSize = 1)
@AllArgsConstructor
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MEMBER")
    private Long memberId;
    @Email
    private String memberEmail;
    private String memberPassword;
    private String memberSalt;
    private String memberName;
    private String memberNickname;
    private char memberGender;
    private LocalDateTime memberBirthday;
    private String memberPhone;
    private LocalDateTime memberRecentlyDate;
    private String memberSocial;

}

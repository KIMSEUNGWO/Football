package football.start.allOfFootball.controller.login;

import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.enums.GenderEnum;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.regex.Matcher;

@ToString
@Getter
@Setter
public class RegisterDto{

    @NotNull
    @NotEmpty
    @NotBlank
    @Email(regexp = "^[0-9a-zA-Z]+@[a-z]+\\.[a-z]{1,4}",message = "유효하지 않은 이메일입니다.")
    private String email;

    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+{}\\[\\]:;<>,.?~\\\\/-]).{8,15}$", message = "대,소문자와 특수문자를 포함한 8~15자만 가능합니다.")
    private String password;

    @NotNull
    private String passwordCheck;

    @Pattern(regexp = "([가-힣]+)|([a-zA-Z]+)", message = "이름 정보가 잘못입력되었습니다.")
    private String name;

    @Pattern(regexp = "(남자)|(여자)", message = "성별 정보가 잘못입력되었습니다.")
    private String gender;

//    @Pattern(regexp = "[0-9]{4}-[0-9]{2}-[0-9]{2}$", message = "생년월일 정보가 잘못입력되었습니다.") // 1999-01-01
    @Pattern(regexp = "[0-9]{6}", message = "생년월일 정보가 잘못입력되었습니다.")
    private String birthday;

    @Pattern(regexp = "(010|011)-[0-9]{3,4}-[0-9]{4}", message = "휴대폰 정보가 잘못입력되었습니다.")
    private String phone;

    public RegisterDto() {
    }

    public Member builder() {
        return Member.builder()
            .memberEmail(email)
            .memberPassword(password)
            .memberName(name)
            .memberGender(GenderEnum.valueOf(gender))
            .memberBirthday(getLocalDate(birthday))
            .memberPhone(phone)
            .build();
    }

    private LocalDate getLocalDate(String birthday) {
        java.util.regex.Pattern p = java.util.regex.Pattern.compile("[0-9]{2}");
        Matcher m = p.matcher(birthday);
        m.find();
        int year = Integer.parseInt(m.group());
        m.find();
        int month = Integer.parseInt(m.group());
        m.find();
        int day = Integer.parseInt(m.group());
        return LocalDate.of(year, month, day);
    }
}

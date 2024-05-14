package football.start.allOfFootball.controller.api.kakaoLogin;

import football.common.enums.matchenum.GenderEnum;
import football.common.enums.SocialEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
@ToString
public class LoginResponse {

    private SocialEnum socialType;
    private Integer id;
    private String nickName;
    private String profile;
    private String email;
    private GenderEnum gender;
    private LocalDate birthday;
    private String phone;

}

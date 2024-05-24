package football.login.config.oauth;

import football.common.enums.SocialEnum;
import football.common.enums.matchenum.GenderEnum;

import java.time.LocalDate;

public interface OAuth2MemberInfo {

    String getEmail();
    String getPhone();
    SocialEnum getSocialType();
    String getName();
    LocalDate getBirthDay();
    GenderEnum getGender();
    String getProfileUri();
    Long getId();

}

package football.login.config.oauth;

import football.common.enums.SocialEnum;
import football.common.enums.matchenum.GenderEnum;
import football.common.formatter.DateFormatter;

import java.time.LocalDate;
import java.util.Map;

public class KakaoMemberInfo implements OAuth2MemberInfo {

    private final Map<String, Object> attributes;
    private final Map<String, Object> properties;
    private final Map<String, Object> kakaoAccount;

    public KakaoMemberInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
        this.properties = (Map<String, Object>) attributes.get("properties");
        this.kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
    }

    @Override
    public String getEmail() {
        return (String) kakaoAccount.getOrDefault("email", "");
    }

    @Override
    public String getPhone() {
        String phone = (String) kakaoAccount.get("phone_number");
        int index = phone.indexOf("0"); // 0-0000-0000
        return "01" +  phone.substring(index).replace("-", ""); // 01000000000
    }

    @Override
    public SocialEnum getSocialType() {
        return SocialEnum.KAKAO;
    }

    @Override
    public String getName() {
        return (String) properties.get("nickname");
    }

    @Override
    public LocalDate getBirthDay() {
        String birthYear = (String) kakaoAccount.get("birthyear");
        String birthDay = (String) kakaoAccount.get("birthday");
        return DateFormatter.toLocalDate(birthYear + birthDay, "yyyyMMdd");
    }

    @Override
    public GenderEnum getGender() {
        String gender = (String) kakaoAccount.get("gender");
        return GenderEnum.getGender(gender);
    }

    @Override
    public String getProfileUri() {
        return (String) properties.get("profile_image");
    }

    @Override
    public Long getId() {
        return (Long) attributes.get("id");
    }


}

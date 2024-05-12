package football.internal.database.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SocialEnum {

    KAKAO("kakao"),
    NAVER("naver");

    private String className;
}

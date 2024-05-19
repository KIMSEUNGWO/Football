package football.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public enum SocialEnum {

    KAKAO("kakao"),
    NAVER("naver");

    private String className;

    SocialEnum(String className) {
        this.className = className;
    }
}

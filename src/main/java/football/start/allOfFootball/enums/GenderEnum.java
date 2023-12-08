package football.start.allOfFootball.enums;

public enum GenderEnum {

    전체("남녀무관"),
    남자("남자만"),
    여자("여자만");

    public String matchInfo;

    GenderEnum(String matchInfo) {
        this.matchInfo = matchInfo;
    }
}

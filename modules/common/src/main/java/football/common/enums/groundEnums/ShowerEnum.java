package football.common.enums.groundEnums;

public enum ShowerEnum {

    있음("샤워장 있음"),
    없음("샤워장 없음");

    public String matchInfo;

    ShowerEnum(String matchInfo) {
        this.matchInfo = matchInfo;
    }
}

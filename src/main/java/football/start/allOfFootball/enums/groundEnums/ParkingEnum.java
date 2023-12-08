package football.start.allOfFootball.enums.groundEnums;

public enum ParkingEnum {

    무료("무료주차"),
    유료("유료주차"),
    불가("주차장 사용 불가");

    public String matchInfo;

    ParkingEnum(String matchInfo) {
        this.matchInfo = matchInfo;
    }
}

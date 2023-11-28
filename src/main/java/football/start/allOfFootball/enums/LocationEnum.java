package football.start.allOfFootball.enums;

public enum LocationEnum {

    전체("regionAll"),
    서울("seoul"),
    경기("gyeonggi"),
    인천("incheon"),
    강원("gangwon"),
    대전("daejeon"),
    세종("sejong"),
    충남("chungnam"),
    충북("chungbuk"),
    대구("daegu"),
    경북("gyeongbuk"),
    경남("gyeongnam"),
    부산("busan"),
    울산("ulsan"),
    광주("gwangju"),
    전북("jeonbuk"),
    전남("jeonnam"),
    제주("jeju");

    private String regionId;

    LocationEnum(String regionId) {
        this.regionId = regionId;
    }

    public String getRegionId() {
        return regionId;
    }
}

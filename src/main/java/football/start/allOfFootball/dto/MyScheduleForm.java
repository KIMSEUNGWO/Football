package football.start.allOfFootball.dto;

import football.common.enums.domainenum.LocationEnum;

public record MyScheduleForm(Long matchId, String hour, LocationEnum region, String fieldTitle) {

}

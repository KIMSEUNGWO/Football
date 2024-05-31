package football.start.allOfFootball.controller.mypage;

import football.common.enums.matchenum.MatchStatus;

/**
 * @param matchTime         18:00
 * @param maxPersonAndCount 6 vs 6 3파전
 * @param nowPerson         7 / 18
 */
public record MatchDataForm(Long matchId, String matchTime, String maxPersonAndCount, String fieldTitle,
                            String nowPerson, MatchStatus matchStatus) {

}

package football.start.allOfFootball.mapper;

import football.common.consts.Constant;
import football.common.domain.*;
import football.common.formatter.DateFormatter;
import football.common.formatter.NumberFormatter;
import football.start.allOfFootball.controller.mypage.MatchDataForm;
import football.start.allOfFootball.controller.mypage.MyProfileDto;
import football.start.allOfFootball.controller.mypage.MypageMainDto;
import football.start.allOfFootball.dto.CouponListForm;
import football.start.allOfFootball.dto.MatchViewForm;
import football.start.allOfFootball.dto.OrderForm;
import football.start.allOfFootball.dto.SearchResultForm;

import java.util.List;
import java.util.Optional;

import static football.common.formatter.NumberFormatter.format;

public class Mapper {


    public static SearchResultForm toSearchResultForm(Match match) {
        return new SearchResultForm(
            match.getMatchId(),
            DateFormatter.format("HH:mm",match.getMatchDate()),
            match.getField().getFieldLocation(),
            match.getField().getFieldTitle(),
            match.getMatchGender(),
            match.getMatchGrade(),
            String.format("%d vs %d",match.getMaxPerson(), match.getMaxPerson()),
            match.getMatchStatus());
    }

    public static OrderForm toOrderForm(Member member, Match match, List<CouponListForm> couponList) {
        return new OrderForm(
            match.getMatchId(),
            match.getField().getFieldTitle(),
            match.getField().getFieldAddress(),
            DateFormatter.format("M월 d일 EEEE HH:mm", match.getMatchDate()),
            NumberFormatter.format(match.getPrice()),
            couponList,
            member.getMemberCash()
        );

    }

    public static MatchViewForm toMatchViewForm(Match match) {
        Field field = match.getField();
        return new MatchViewForm(
            match.getMatchId(),
            field.getFieldImages(),
            match.getMatchGrade(),
            match.getMatchGender(),
            getPersonAndCount(match),
            match.getManager() == null ? null : match.getManager().getMember().getMemberName(),
            field.getFieldSize(),
            field.getFieldParking(),
            field.getFieldShower(),
            field.getFieldToilet(),
            field.getFieldInformation(),
            DateFormatter.format("M월 d일 EEEE HH:mm", match.getMatchDate()),
            field.getFieldTitle(),
            field.getFieldAddress(),
            NumberFormatter.format(match.getPrice()),
            match.getMatchStatus()
        );
    }

    public static MypageMainDto getMyPageMain(Member member, Optional<BeforePassword> findBeforePassword) {
        return new MypageMainDto(
            getProfileImage(member.getProfile()),
            member.getMemberName(),
            member.getMemberPhone(),
            findBeforePassword.map(BeforePassword::getConvertChangeDate).orElse("변경된 기록 없음")
            );
    }

    public static MyProfileDto getMyProfileDto(Member member, long myRank) {
        return new MyProfileDto(
            getProfileImage(member.getProfile()),
            member.getMemberName(),
            member.getSocial(),
            member.getMemberEmail(),
            format(member.getMemberScore()),
            format(myRank),
            member.getGrade(),
            null,
            format(member.getMemberCash())
        );
//        myProfileDto.setMatchScore();
    }

    public static MatchDataForm getMatchDataForm(Match match, List<Orders> ordersList) {
        return new MatchDataForm(
            match.getMatchId(),
            DateFormatter.format("HH:mm", match.getMatchDate()),
            getPersonAndCount(match),
            match.getField().getFieldTitle(),
            ordersList.size() + " / " + (match.getMaxPerson() * match.getMatchCount()),
            match.getMatchStatus()
            );
    }

    public static CouponListForm getCouponListForm(CouponList couponList) {
        return new CouponListForm(
            couponList.getCouponListId(),
            couponList.getCoupon().getCouponName(),
            couponList.getCoupon().getCouponDiscount(),
            couponList.howToRemaining(),
            DateFormatter.format("yyyy.MM.dd HH:mm 까지", couponList.getCouponListExpireDate())
        );
    }

    private static String getProfileImage(Profile profile) {
        if (profile == null) return Constant.BASE_IMG;
        return profile.getStoreName();
    }
    private static String getPersonAndCount(Match match) {
        int maxPerson = match.getMaxPerson();
        return String.format("%dm vs %dm %d파전", maxPerson, maxPerson, match.getMatchCount());
    }

}

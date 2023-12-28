package football.start.allOfFootball.service;

import football.start.allOfFootball.controller.mypage.ManagerDataForm;
import football.start.allOfFootball.controller.mypage.MatchDataForm;
import football.start.allOfFootball.controller.mypage.MyProfileDto;
import football.start.allOfFootball.controller.mypage.MypageMainDto;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.dto.ChangePasswordForm;
import football.start.allOfFootball.dto.match.TeamInfo;
import football.start.allOfFootball.enums.TeamEnum;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MypageService {

    MyProfileDto getMyProfile(Member findMember);

    Map<String, String> changePassword(Optional<Member> findMember, ChangePasswordForm form, BindingResult bindingResult);

    MypageMainDto getMypageMain(Member findMember);

    Map<String, List<ManagerDataForm>> getManagerList(Member findMember);
}

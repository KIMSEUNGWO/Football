package football.start.allOfFootball.service;

import football.start.allOfFootball.controller.mypage.ManagerDataForm;
import football.start.allOfFootball.controller.mypage.MyProfileDto;
import football.start.allOfFootball.controller.mypage.MypageMainDto;
import football.internal.database.domain.Member;
import football.start.allOfFootball.dto.ChangePasswordForm;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;

public interface MypageService {

    MyProfileDto getMyProfile(Member findMember);

    Map<String, String> changePassword(Member member, ChangePasswordForm form, BindingResult bindingResult);

    MypageMainDto getMypageMain(Member findMember);

    Map<String, List<ManagerDataForm>> getManagerList(Member findMember);
}

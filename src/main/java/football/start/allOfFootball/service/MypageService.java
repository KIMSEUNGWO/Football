package football.start.allOfFootball.service;

import football.start.allOfFootball.controller.mypage.MyProfileDto;
import football.start.allOfFootball.controller.mypage.MypageMainDto;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.dto.ChangePasswordForm;
import org.springframework.validation.BindingResult;

import java.util.Map;
import java.util.Optional;

public interface MypageService {
    Optional<Member> findById(Long memberId);

    MyProfileDto getMyProfile(Member findMember);

    Map<String, String> changePassword(Optional<Member> findMember, ChangePasswordForm form, BindingResult bindingResult);

    MypageMainDto getMypageMain(Member findMember);
}

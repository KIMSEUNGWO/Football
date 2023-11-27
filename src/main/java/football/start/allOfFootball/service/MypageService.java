package football.start.allOfFootball.service;

import football.start.allOfFootball.controller.mypage.MyProfileDto;
import football.start.allOfFootball.domain.Member;

import java.util.Optional;

public interface MypageService {
    Optional<Member> findById(Long memberId);

    MyProfileDto getMyProfile(Member findMember);
}

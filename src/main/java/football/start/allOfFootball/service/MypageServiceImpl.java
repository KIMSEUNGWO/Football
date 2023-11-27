package football.start.allOfFootball.service;

import football.start.allOfFootball.controller.mypage.MyProfileDto;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.domain.Profile;
import football.start.allOfFootball.enums.SocialEnum;
import football.start.allOfFootball.formatter.NumberFormatter;
import football.start.allOfFootball.repository.MypageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.Optional;

import static football.start.allOfFootball.formatter.NumberFormatter.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MypageServiceImpl implements MypageService{

    private final MypageRepository mypageRepository;

    @Override
    public Optional<Member> findById(Long memberId) {
        return mypageRepository.findById(memberId);
    }

    @Override
    public MyProfileDto getMyProfile(Member findMember) {
        MyProfileDto myProfileDto = new MyProfileDto();
        Profile profile = findMember.getProfile();
        if (profile != null) {
            myProfileDto.setProfileImage(profile.getProfileStoreName());
        }
        myProfileDto.setName(findMember.getMemberName());
        SocialEnum memberSocial = findMember.getMemberSocial();
        if (memberSocial != null) {
            myProfileDto.setSocial(memberSocial.name());
        }
        myProfileDto.setEmail(findMember.getMemberEmail());
        myProfileDto.setScore(format(findMember.getMemberScore()));
//        myProfileDto.setRank();
        myProfileDto.setGrade(findMember.getGrade());
//        myProfileDto.setMatchScore();

        return myProfileDto;
    }
}

package football.start.allOfFootball.service;

import football.start.allOfFootball.common.BCrypt;
import football.start.allOfFootball.common.file.FileService;
import football.start.allOfFootball.controller.api.kakaoLogin.LoginResponse;
import football.start.allOfFootball.controller.login.EmailDto;
import football.start.allOfFootball.domain.KakaoToken;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.domain.Social;
import football.start.allOfFootball.dto.json.JsonDefault;
import football.start.allOfFootball.enums.FileUploadType;
import football.start.allOfFootball.enums.SocialEnum;
import football.start.allOfFootball.enums.gradeEnums.GradeEnum;
import football.start.allOfFootball.jpaRepository.JpaKakaoTokenRepository;
import football.start.allOfFootball.repository.LoginRepository;
import football.start.allOfFootball.repository.RegisterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService{

    private final JpaKakaoTokenRepository jpaKakaoTokenRepository;
    private final RegisterRepository registerRepository;
    private final LoginRepository loginRepository;
    private final FileService fileService;
    private final BCrypt bc;

    @Override
    public ResponseEntity<JsonDefault> validEmail(EmailDto emailDto) {
        String email = emailDto.getEmail();
        Optional<Member> findMember = registerRepository.findByMemberEmail(email);
        if (findMember.isPresent()) {
            return new ResponseEntity<>(new JsonDefault("error", "중복된 이메일입니다."), HttpStatus.OK);
        }
        return new ResponseEntity<>(new JsonDefault("ok", "사용가능한 이메일입니다."), HttpStatus.OK);
    }

    @Override
    public void save(Member member) {
        member.setMemberSalt();

        String memberPassword = member.combineSalt(member.getMemberPassword());
        String encodePassword = bc.encodeBCrypt(memberPassword);
        member.setMemberPassword(encodePassword);


        registerRepository.save(member);
    }

    @Override
    public boolean distinctEmail(String email) {
        Optional<Member> byMember = loginRepository.findByMemberEmail(email);
        return byMember.isPresent();
    }

    @Override
    @Transactional
    public Member socialSave(LoginResponse userInfo, KakaoToken kakaoToken) {
        SocialEnum type = userInfo.getSocialType();
        Integer id = userInfo.getId();
        String profile = userInfo.getProfile();

        Member saveMember = Member.builder()
            .memberEmail(userInfo.getEmail())
            .memberName(userInfo.getNickName())
            .grade(GradeEnum.루키)
            .memberGender(userInfo.getGender())
            .memberBirthday(userInfo.getBirthday())
            .memberPhone(userInfo.getPhone())
            .build();

        registerRepository.save(saveMember);

        Social saveSocial = Social.builder()
            .member(saveMember)
            .socialType(type)
            .socialNum(id)
            .build();
        registerRepository.saveSocial(saveSocial);

        kakaoToken.setSocial(saveSocial);
        jpaKakaoTokenRepository.save(kakaoToken);

        fileService.saveImage(profile, saveMember, FileUploadType.PROFILE);
        return saveMember;
    }
}

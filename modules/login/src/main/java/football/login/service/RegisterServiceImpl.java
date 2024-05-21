package football.login.service;

import football.common.common.BCrypt;
import football.common.domain.KakaoToken;
import football.common.domain.Member;
import football.common.domain.Social;
import football.common.dto.json.JsonDefault;
import football.common.enums.SocialEnum;
import football.common.enums.gradeEnums.GradeEnum;
import football.common.jpaRepository.JpaKakaoTokenRepository;
import football.file.enums.FileUploadType;
import football.file.service.FileService;
import football.login.dto.EmailDto;
import football.login.dto.LoginResponse;
import football.login.repository.LoginRepository;
import football.login.repository.RegisterRepository;
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
        return loginRepository.existsByPhone(email);
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

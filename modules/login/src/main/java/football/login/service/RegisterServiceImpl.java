package football.login.service;

import football.common.common.BCrypt;
import football.common.domain.KakaoToken;
import football.common.domain.Member;
import football.common.domain.Social;
import football.common.enums.SocialEnum;
import football.common.enums.gradeEnums.GradeEnum;
import football.common.jpaRepository.JpaKakaoTokenRepository;
import football.file.enums.FileUploadType;
import football.file.service.FileService;
import football.login.dto.LoginResponse;
import football.login.repository.LoginRepository;
import football.login.repository.RegisterRepository;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static football.common.consts.SessionConst.REGISTER;

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
    public boolean existsByEmail(String email) {
        return loginRepository.existsByEmail(email);
    }

    @Override
    public void save(Member member) {

        String encodePassword = bc.encodeBCrypt(member.getMemberPassword());
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

    @Override
    public Cookie createCertCookie() {
        return createCookie(10);
    }

    @Override
    public Cookie removeCertCookie() {
        return createCookie(0);
    }

    private Cookie createCookie(int minute) {
        Cookie cookie = new Cookie(REGISTER, REGISTER);
        cookie.setHttpOnly(true); // HttpOnly 속성 설정
        cookie.setPath("/register");
        cookie.setMaxAge(60 * minute); // 쿠키 유효기간: 10분
        return cookie;
    }
}

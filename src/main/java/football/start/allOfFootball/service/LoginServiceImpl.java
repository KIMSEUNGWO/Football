package football.start.allOfFootball.service;

import football.common.domain.Member;
import football.common.enums.SocialEnum;
import football.start.allOfFootball.repository.LoginRepository;
import football.start.allOfFootball.repository.domainRepository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{

    private final LoginRepository loginRepository;
    private final MemberRepository memberRepository;

    @Override
    public Optional<Member> login(String email, String password) {
        Optional<Member> loginMember = loginRepository.findByEmail(email);
        if (loginMember.isEmpty()) return Optional.empty();

        Member findMember = loginMember.get();
        boolean isLogin = memberRepository.isExactPassword(findMember, password);
        if (!isLogin) {
            return Optional.empty();
        }
        // 최근 로그인 시간 갱신
        loginRepository.renewLoginTime(findMember);

        return loginMember;
    }


    @Override
    public Member socialLogin(String email, SocialEnum socialEnum, int loginUser_id) {
        return loginRepository.socialLogin(email, socialEnum, loginUser_id);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return loginRepository.existsByPhone(phone);
    }

    @Override
    public void renewLoginTime(Member loginMember) {
        loginRepository.renewLoginTime(loginMember);
    }
}

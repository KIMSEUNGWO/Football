package football.start.allOfFootball.service;

import football.internal.database.domain.Member;
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
        Optional<Member> loginMember = loginRepository.findByMemberEmail(email);
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
    public Optional<Member> findByEmail(String email) {
        return loginRepository.findByMemberEmail(email);
    }

    @Override
    public boolean findByPhone(String phone) {
        Optional<Member> findMember = loginRepository.findByPhone(phone);
        return findMember.isPresent();
    }

    @Override
    public void renewLoginTime(Member loginMember) {
        loginRepository.renewLoginTime(loginMember);
    }
}

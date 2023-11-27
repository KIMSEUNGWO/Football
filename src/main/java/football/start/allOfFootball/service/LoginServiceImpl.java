package football.start.allOfFootball.service;

import football.start.allOfFootball.common.BCrypt;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{

    private final LoginRepository loginRepository;

    @Override
    public Optional<Member> login(String email, String password) {
        Optional<Member> loginMember = loginRepository.findByMember(email);
        if (loginMember.isEmpty()) return Optional.empty();

        Member findMember = loginMember.get();
        boolean isLogin = BCrypt.matchBCrypt(findMember.combineSalt(password), findMember.getMemberPassword());
        if (!isLogin) {
            return Optional.empty();
        }
        // 최근 로그인 시간 갱신
        loginRepository.renewLoginTime(findMember);

        return loginMember;
    }
}

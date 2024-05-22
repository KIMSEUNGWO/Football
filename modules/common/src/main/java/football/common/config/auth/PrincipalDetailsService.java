package football.common.config.auth;

import football.common.domain.Member;
import football.common.jpaRepository.JpaMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final JpaMemberRepository jpaMemberRepository;

    @Override
    public PrincipalDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("PrincipalDetailsService loadUserByUsername 실행");
        System.out.println("username = " + username);
        Member member = jpaMemberRepository.findByMemberEmail(username).orElseThrow(() -> {
            System.out.println("UsernameNotFoundException");
            return new UsernameNotFoundException("존재하지 않는 회원입니다.");
        });
        System.out.println("member.getMemberEmail() = " + member.getMemberEmail());
        return new PrincipalDetails(member);
    }
}

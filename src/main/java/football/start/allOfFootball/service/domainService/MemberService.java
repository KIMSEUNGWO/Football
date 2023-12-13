package football.start.allOfFootball.service.domainService;

import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.repository.domainRepository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    public Optional<Member> findByMemberId(Long memberId) {
        if (memberId == null) return Optional.empty();
        return memberRepository.findByMemberId(memberId);
    }
}

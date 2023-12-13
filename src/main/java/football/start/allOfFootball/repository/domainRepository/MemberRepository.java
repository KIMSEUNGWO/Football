package football.start.allOfFootball.repository.domainRepository;


import football.start.allOfFootball.common.BCrypt;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.jpaRepository.JpaMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class MemberRepository {

    private final JpaMemberRepository jpaMemberRepository;
    private final BCrypt bc;

    public boolean isExactPassword(Member member, String password) {
        return bc.matchBCrypt(member.combineSalt(password), member.getMemberPassword());
    }


    public Optional<Member> findByMemberId(Long memberId) {
        return jpaMemberRepository.findById(memberId);
    }

    public void refreshMemberCache(Member member, Integer resultCash) {
        member.setMemberCash(resultCash);
    }
}

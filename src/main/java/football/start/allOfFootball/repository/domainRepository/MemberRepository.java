package football.start.allOfFootball.repository.domainRepository;


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


    public Optional<Member> findByMemberId(Long memberId) {
        return jpaMemberRepository.findById(memberId);
    }

    public void refreshMemberCache(Member member, Integer resultCash) {
        member.setMemberCash(resultCash);
    }
}

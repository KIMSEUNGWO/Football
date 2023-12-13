package football.start.allOfFootball.repository.domainRepository;


import football.start.allOfFootball.common.BCrypt;
import football.start.allOfFootball.domain.BeforePassword;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.jpaRepository.JpaBeforePasswordRepository;
import football.start.allOfFootball.jpaRepository.JpaMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class MemberRepository {

    private final JpaMemberRepository jpaMemberRepository;
    private final JpaBeforePasswordRepository jpaBeforePasswordRepository;
    private final BCrypt bc;

    public boolean isExactPassword(Member member, String password) {
        return bc.matchBCrypt(member.combineSalt(password), member.getMemberPassword());
    }
    public boolean isExactPassword(String myPassword, String inputPassword) {
        return bc.matchBCrypt(inputPassword, myPassword);
    }


    public Optional<Member> findByMemberId(Long memberId) {
        return jpaMemberRepository.findById(memberId);
    }

    public void refreshMemberCache(Member member, Integer resultCash) {
        member.setMemberCash(resultCash);
    }

    @Transactional
    public void changePassword(Member member, String changePassword) {
        Optional<BeforePassword> findBeforePassword = jpaBeforePasswordRepository.findById(member.getMemberId());
        if (findBeforePassword.isEmpty()) {
            BeforePassword build = BeforePassword.builder().beforePassword(member.getMemberPassword()).member(member).passwordChangeDate(LocalDateTime.now()).build();
            jpaBeforePasswordRepository.save(build);
        } else {
            BeforePassword beforePassword = findBeforePassword.get();
            beforePassword.setBeforePassword(member.getMemberPassword());
            beforePassword.setPasswordChangeDate(LocalDateTime.now());
        }

        String newPassword = member.combineSalt(changePassword);
        String encode = bc.encodeBCrypt(newPassword);
        member.setMemberPassword(encode);
    }

    public Optional<BeforePassword> findByBeforePassword(Member findMember) {
        return jpaBeforePasswordRepository.findByMember(findMember);
    }
}

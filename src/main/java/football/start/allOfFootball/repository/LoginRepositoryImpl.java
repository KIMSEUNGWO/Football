package football.start.allOfFootball.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.jpaRepository.JpaMemberRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class LoginRepositoryImpl implements LoginRepository {

    private final JpaMemberRepository jpaMemberRepository;

    private final JPAQueryFactory query;

    public LoginRepositoryImpl(JpaMemberRepository jpaMemberRepository, EntityManager em) {
        this.jpaMemberRepository = jpaMemberRepository;
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Optional<Member> findByMemberId(Long memberId) {
        return jpaMemberRepository.findById(memberId);
    }

    @Override
    public Optional<Member> findByMember(String email) {
        return jpaMemberRepository.findByEmail(email);
    }

    @Override
    public void renewLoginTime(Member member) {
        member.setMemberRecentlyDate(LocalDateTime.now());
    }

}

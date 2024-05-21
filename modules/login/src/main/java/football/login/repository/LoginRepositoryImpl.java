package football.login.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import football.common.domain.Member;
import football.common.domain.QMember;
import football.common.enums.SocialEnum;
import football.common.jpaRepository.JpaMemberRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static football.common.domain.QMember.member;
import static football.common.domain.QSocial.social;


@Slf4j
@Repository
@Transactional
public class LoginRepositoryImpl implements LoginRepository {

    private final JpaMemberRepository jpaMemberRepository;

    private final JPAQueryFactory query;

    public LoginRepositoryImpl(JpaMemberRepository jpaMemberRepository, EntityManager em) {
        this.jpaMemberRepository = jpaMemberRepository;
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return jpaMemberRepository.findByMemberEmail(email);
    }

    @Override
    public Member socialLogin(String email, SocialEnum socialEnum, int loginUser_id) {
        return query.select(member)
            .from(member)
            .join(social).on(member.memberId.eq(social.member.memberId))
            .where(member.memberEmail.eq(email)
                .and(social.socialType.eq(socialEnum))
                .and(social.socialNum.eq(loginUser_id))
            ).fetchFirst();
    }

    @Override
    public void renewLoginTime(Member member) {
        query.update(QMember.member)
            .set(QMember.member.memberRecentlyDate, LocalDateTime.now())
            .where(QMember.member.memberId.eq(member.getMemberId()));
    }

    @Override
    public boolean existsByPhone(String phone) {
        return jpaMemberRepository.existsByMemberPhone(phone);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaMemberRepository.existsByMemberEmail(email);
    }

}

package football.api.kakaologin.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import football.common.domain.Token;
import football.common.enums.SocialEnum;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static football.common.domain.QMember.*;
import static football.common.domain.QSocial.*;
import static football.common.domain.QToken.*;

@Repository
public class KakaoLoginRepository {

    private final JPAQueryFactory query;

    @Autowired
    public KakaoLoginRepository(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    public Token findByKakaoToken(Long memberId, SocialEnum socialEnum) {
        return query.select(token)
            .from(member)
            .join(social).on(member.memberId.eq(social.member.memberId))
            .join(token).on(social.token.tokenId.eq(token.tokenId))
            .where(member.memberId.eq(memberId).and(social.socialType.eq(socialEnum)))
            .fetchFirst();
    }
}

package football.start.allOfFootball.controller.api.kakaoLogin;

import com.querydsl.jpa.impl.JPAQueryFactory;
import football.common.domain.KakaoToken;
import football.common.domain.QKakaoToken;
import football.common.domain.QMember;
import football.common.domain.QSocial;
import football.common.enums.SocialEnum;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static football.common.domain.QKakaoToken.kakaoToken;
import static football.common.domain.QMember.*;
import static football.common.domain.QSocial.*;

@Repository
public class KakaoLoginRepository {

    private final JPAQueryFactory query;

    @Autowired
    public KakaoLoginRepository(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }


    public KakaoToken findByKakaoToken(Long memberId, SocialEnum socialEnum) {
        return query.select(kakaoToken)
            .from(member)
            .join(social).on(member.memberId.eq(social.member.memberId))
            .join(kakaoToken).on(social.kakaoToken.kakaoTokenId.eq(kakaoToken.kakaoTokenId))
            .where(member.memberId.eq(memberId).and(social.socialType.eq(socialEnum)))
            .fetchFirst();
    }
}

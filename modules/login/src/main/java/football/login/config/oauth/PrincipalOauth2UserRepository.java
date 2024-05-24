package football.login.config.oauth;

import com.querydsl.jpa.impl.JPAQueryFactory;
import football.common.domain.Member;
import football.common.domain.Social;
import football.common.domain.Token;
import football.common.enums.Role;
import football.common.enums.SocialEnum;
import football.common.enums.gradeEnums.GradeEnum;
import football.common.jpaRepository.JpaTokenRepository;
import football.common.jpaRepository.JpaMemberRepository;
import football.common.jpaRepository.JpaSocialRepository;
import football.file.enums.FileUploadType;
import football.file.service.FileService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

import static football.common.domain.QMember.member;
import static football.common.domain.QSocial.social;

@Repository
public class PrincipalOauth2UserRepository {

    private final JpaMemberRepository jpaMemberRepository;
    private final JpaSocialRepository jpaSocialRepository;
    private final JpaTokenRepository jpaTokenRepository;
    private final FileService fileService;
    private final JPAQueryFactory query;

    @Autowired
    public PrincipalOauth2UserRepository(JpaMemberRepository jpaMemberRepository, JpaSocialRepository jpaSocialRepository, JpaTokenRepository jpaTokenRepository, FileService fileService, EntityManager em) {
        this.jpaMemberRepository = jpaMemberRepository;
        this.jpaSocialRepository = jpaSocialRepository;
        this.jpaTokenRepository = jpaTokenRepository;
        this.fileService = fileService;
        this.query = new JPAQueryFactory(em);
    }

    public Member socialSave(OAuth2MemberInfo info, String accessToken) {

        Member saveMember = Member.builder()
            .memberEmail(info.getEmail())
            .memberName(info.getName())
            .grade(GradeEnum.루키)
            .memberGender(info.getGender())
            .memberBirthday(info.getBirthDay())
            .memberPhone(info.getPhone())
            .role(Role.USER)
            .createDate(LocalDateTime.now())
            .build();

        jpaMemberRepository.save(saveMember);

        Social saveSocial = Social.builder()
            .member(saveMember)
            .socialType(info.getSocialType())
            .socialNum(info.getId())
            .build();
        jpaSocialRepository.save(saveSocial);

        Token token = Token.builder()
            .social(saveSocial)
            .access_token(accessToken)
            .build();
        jpaTokenRepository.save(token);

        fileService.saveImage(info.getProfileUri(), saveMember, FileUploadType.PROFILE);
        return jpaMemberRepository.findById(saveMember.getMemberId()).get();
    }

    public Member socialLogin(String email, SocialEnum socialEnum, Long loginUser_id) {
        return query.select(member)
            .from(member)
            .join(social).on(member.memberId.eq(social.member.memberId))
            .where(member.memberEmail.eq(email)
                .and(social.socialType.eq(socialEnum))
                .and(social.socialNum.eq(loginUser_id))
            ).fetchFirst();
    }
}

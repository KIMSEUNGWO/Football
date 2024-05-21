package football.common.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import football.common.common.BCrypt;
import football.common.domain.BeforePassword;
import football.common.domain.Member;
import football.common.domain.Orders;
import football.common.jpaRepository.JpaBeforePasswordRepository;
import football.common.jpaRepository.JpaMemberRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static football.common.domain.QOrders.orders;


@Repository
@Slf4j
public class MemberRepository {

    private final JpaMemberRepository jpaMemberRepository;
    private final JpaBeforePasswordRepository jpaBeforePasswordRepository;
    private final JPAQueryFactory query;
    private final BCrypt bc;

    @Autowired
    public MemberRepository(JpaMemberRepository jpaMemberRepository, JpaBeforePasswordRepository jpaBeforePasswordRepository, EntityManager em, BCrypt bc) {
        this.jpaMemberRepository = jpaMemberRepository;
        this.jpaBeforePasswordRepository = jpaBeforePasswordRepository;
        this.query = new JPAQueryFactory(em);
        this.bc = bc;
    }

    public boolean isExactPassword(Member member, String password) {
        return bc.matchBCrypt(member.combineSalt(password), member.getMemberPassword());
    }
    public boolean isExactPassword(String myPassword, String inputPassword) {
        return bc.matchBCrypt(inputPassword, myPassword);
    }


    public Optional<Member> findByMemberId(Long memberId) {
        return jpaMemberRepository.findById(memberId);
    }


    @Transactional
    public void changePassword(Member member, String changePassword) {
        Optional<BeforePassword> findBeforePassword = jpaBeforePasswordRepository.findById(member.getMemberId());
        findBeforePassword.ifPresentOrElse(
            // Present
            beforePassword -> beforePassword.changeBeforePassword(member.getMemberPassword()),
            // Empty
            () -> jpaBeforePasswordRepository.save(new BeforePassword(member))
        );

        String newPassword = member.combineSalt(changePassword);
        String encode = bc.encodeBCrypt(newPassword);
        member.setMemberPassword(encode);
    }

    public Optional<BeforePassword> findByBeforePassword(Member findMember) {
        return jpaBeforePasswordRepository.findByMember(findMember);
    }

    public List<Orders> findAllOrders(Member member) {
        return query.selectFrom(orders)
            .where(orders.member.eq(member).and(orders.match.matchDate.after(LocalDateTime.now())))
            .fetch();
    }

    public Optional<Member> findByMemberPhone(String phone) {
        return jpaMemberRepository.findByMemberPhone(phone);
    }

    public Optional<Member> findByMemberEmailAndMemberPhone(String email, String phone) {
        return jpaMemberRepository.findByMemberEmailAndMemberPhone(email, phone);
    }

    public List<Member> findAll() {
        return jpaMemberRepository.findAll();
    }
}

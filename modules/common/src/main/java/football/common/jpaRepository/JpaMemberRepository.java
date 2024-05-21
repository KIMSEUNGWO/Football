package football.common.jpaRepository;

import football.common.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaMemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByMemberEmail(String memberEmail);
    Optional<Member> findByMemberPhone(String memberPhone);
    Optional<Member> findByMemberEmailAndMemberPhone(String memberEmail, String memberPhone);
    boolean existsByMemberPhone(String memberPhone);
    boolean existsByMemberEmail(String memberEmail);
    boolean existsByMemberEmailAndMemberPhone(String memberEmail, String memberPhone);
}

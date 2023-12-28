package football.start.allOfFootball.jpaRepository;

import football.start.allOfFootball.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Optional;

public interface JpaMemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByMemberEmail(String memberEmail);
    Optional<Member> findByMemberPhone(String memberPhone);
}

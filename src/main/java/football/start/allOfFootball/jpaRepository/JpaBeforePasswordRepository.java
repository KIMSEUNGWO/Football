package football.start.allOfFootball.jpaRepository;

import football.start.allOfFootball.domain.BeforePassword;
import football.start.allOfFootball.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaBeforePasswordRepository extends JpaRepository<BeforePassword, Long> {

    Optional<BeforePassword> findByMember(Member member);
}

package football.common.jpaRepository;

import football.common.domain.BeforePassword;
import football.common.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaBeforePasswordRepository extends JpaRepository<BeforePassword, Long> {

    Optional<BeforePassword> findByMember(Member member);
}

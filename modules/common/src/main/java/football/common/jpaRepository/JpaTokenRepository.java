package football.common.jpaRepository;

import football.common.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTokenRepository extends JpaRepository<Token, Long> {
}

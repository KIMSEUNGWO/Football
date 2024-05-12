package football.internal.database.jpaRepository;

import football.internal.database.domain.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMatchRepository extends JpaRepository<Match, Long> {
}

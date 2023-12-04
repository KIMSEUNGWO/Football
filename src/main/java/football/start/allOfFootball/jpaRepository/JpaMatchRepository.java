package football.start.allOfFootball.jpaRepository;

import football.start.allOfFootball.domain.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMatchRepository extends JpaRepository<Match, Long> {
}

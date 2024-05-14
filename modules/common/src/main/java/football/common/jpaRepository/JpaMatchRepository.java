package football.common.jpaRepository;

import football.common.domain.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface JpaMatchRepository extends JpaRepository<Match, Long> {

    List<Match> findAllByMatchDateBetween(LocalDateTime start, LocalDateTime end);
}

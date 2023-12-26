package football.start.allOfFootball.jpaRepository;

import football.start.allOfFootball.domain.score.Score;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaScoreRepository extends JpaRepository<Score, Long> {
}

package football.common.jpaRepository;

import football.common.domain.score.Score;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaScoreRepository extends JpaRepository<Score, Long> {
}

package football.internal.database.jpaRepository;

import football.internal.database.domain.score.Score;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaScoreRepository extends JpaRepository<Score, Long> {
}

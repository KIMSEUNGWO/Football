package football.common.jpaRepository;

import football.common.domain.score.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaGoalRepository extends JpaRepository<Goal, Long> {
}

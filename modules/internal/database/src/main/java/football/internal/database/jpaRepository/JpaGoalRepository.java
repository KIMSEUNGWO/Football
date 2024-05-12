package football.internal.database.jpaRepository;

import football.internal.database.domain.score.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaGoalRepository extends JpaRepository<Goal, Long> {
}

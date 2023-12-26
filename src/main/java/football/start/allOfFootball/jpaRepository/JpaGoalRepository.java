package football.start.allOfFootball.jpaRepository;

import football.start.allOfFootball.domain.score.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaGoalRepository extends JpaRepository<Goal, Long> {
}

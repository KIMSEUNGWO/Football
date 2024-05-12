package football.internal.database.jpaRepository;

import football.internal.database.domain.score.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTeamRepository extends JpaRepository<Team, Long> {
}

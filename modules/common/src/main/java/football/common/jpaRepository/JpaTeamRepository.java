package football.common.jpaRepository;

import football.common.domain.score.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTeamRepository extends JpaRepository<Team, Long> {
}

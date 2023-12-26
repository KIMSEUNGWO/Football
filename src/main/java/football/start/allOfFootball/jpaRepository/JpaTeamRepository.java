package football.start.allOfFootball.jpaRepository;

import football.start.allOfFootball.domain.score.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTeamRepository extends JpaRepository<Team, Long> {
}

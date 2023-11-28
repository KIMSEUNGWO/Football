package football.start.allOfFootball.jpaRepository;

import football.start.allOfFootball.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProfileRepository extends JpaRepository<Profile, Long> {
}

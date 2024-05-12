package football.internal.database.jpaRepository;

import football.internal.database.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProfileRepository extends JpaRepository<Profile, Long> {
}

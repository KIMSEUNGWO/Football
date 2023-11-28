package football.start.allOfFootball.jpaRepository;

import football.start.allOfFootball.domain.Field;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaFieldRepository extends JpaRepository<Field, Long> {
}

package football.common.jpaRepository;

import football.common.domain.Field;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaFieldRepository extends JpaRepository<Field, Long> {
}

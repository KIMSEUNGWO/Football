package football.internal.database.jpaRepository;

import football.internal.database.domain.Field;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaFieldRepository extends JpaRepository<Field, Long> {
}

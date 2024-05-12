package football.internal.database.jpaRepository;

import football.internal.database.domain.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaManagerRepository extends JpaRepository<Manager, Long> {

}

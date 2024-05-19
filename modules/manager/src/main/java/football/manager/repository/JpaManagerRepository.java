package football.manager.repository;

import football.common.domain.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaManagerRepository extends JpaRepository<Manager, Long> {

}

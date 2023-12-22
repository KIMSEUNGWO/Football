package football.start.allOfFootball.jpaRepository;

import football.start.allOfFootball.domain.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaManagerRepository extends JpaRepository<Manager, Long> {

}

package football.start.allOfFootball.jpaRepository;

import football.start.allOfFootball.domain.Social;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSocialRepository extends JpaRepository<Social, Long> {
}

package football.common.jpaRepository;

import football.common.domain.Social;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSocialRepository extends JpaRepository<Social, Long> {
}

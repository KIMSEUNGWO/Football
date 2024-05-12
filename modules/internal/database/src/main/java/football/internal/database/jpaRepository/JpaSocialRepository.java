package football.internal.database.jpaRepository;

import football.internal.database.domain.Social;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSocialRepository extends JpaRepository<Social, Long> {
}

package football.start.allOfFootball.jpaRepository;

import football.start.allOfFootball.domain.FieldImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaFieldImageRepository extends JpaRepository<FieldImage, Long> {
}

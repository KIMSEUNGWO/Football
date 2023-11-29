package football.start.allOfFootball.jpaRepository;

import football.start.allOfFootball.domain.Field;
import football.start.allOfFootball.domain.FieldImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaFieldImageRepository extends JpaRepository<FieldImage, Long> {
    List<FieldImage> findAllByField(Field field);
}

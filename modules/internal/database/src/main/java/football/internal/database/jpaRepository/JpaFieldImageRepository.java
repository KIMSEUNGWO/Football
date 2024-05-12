package football.internal.database.jpaRepository;

import football.internal.database.domain.Field;
import football.internal.database.domain.FieldImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaFieldImageRepository extends JpaRepository<FieldImage, Long> {
    List<FieldImage> findAllByField(Field field);

    void deleteByFieldImageStoreName(String fieldImageStoreName);
}

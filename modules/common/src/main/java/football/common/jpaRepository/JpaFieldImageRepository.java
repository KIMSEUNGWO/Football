package football.common.jpaRepository;

import football.common.domain.Field;
import football.common.domain.FieldImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaFieldImageRepository extends JpaRepository<FieldImage, Long> {
    List<FieldImage> findAllByField(Field field);

    void deleteByFieldImageStoreName(String fieldImageStoreName);
}

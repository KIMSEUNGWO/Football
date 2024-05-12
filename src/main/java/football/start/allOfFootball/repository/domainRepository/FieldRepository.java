package football.start.allOfFootball.repository.domainRepository;

import football.internal.database.domain.Field;
import football.internal.database.domain.FieldImage;
import football.internal.database.jpaRepository.JpaFieldImageRepository;
import football.internal.database.jpaRepository.JpaFieldRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
@Transactional
public class FieldRepository {

    private final JpaFieldRepository jpaFieldRepository;
    private final JpaFieldImageRepository jpaFieldImageRepository;

    public void saveField(Field field) {
        jpaFieldRepository.save(field);
    }

    public Optional<Field> findByField(Long fieldId) {
        return jpaFieldRepository.findById(fieldId);
    }

    public List<FieldImage> findByAllFieldImage(Field field) {
        return jpaFieldImageRepository.findAllByField(field);
    }
    public void deleteByFieldImage(String deleteImage) {
        jpaFieldImageRepository.deleteByFieldImageStoreName(deleteImage);
    }
}

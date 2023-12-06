package football.start.allOfFootball.repository.domainRepository;

import football.start.allOfFootball.domain.Field;
import football.start.allOfFootball.domain.FieldImage;
import football.start.allOfFootball.jpaRepository.JpaFieldImageRepository;
import football.start.allOfFootball.jpaRepository.JpaFieldRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
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

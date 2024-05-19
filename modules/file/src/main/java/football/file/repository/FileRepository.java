package football.file.repository;

import football.common.domain.*;
import football.common.jpaRepository.JpaFieldImageRepository;
import football.common.jpaRepository.JpaProfileRepository;
import football.file.dto.FileUploadDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class FileRepository {

    private final EntityManager em;

    public void saveAll(List<ImageChild> images) {
        images.forEach(em::persist);
        em.flush();
        em.clear();
    }
    public void save(ImageChild images) {
        em.persist(images);
        em.flush();
        em.clear();
    }
}

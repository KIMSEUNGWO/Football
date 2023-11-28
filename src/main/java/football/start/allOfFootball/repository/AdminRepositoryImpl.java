package football.start.allOfFootball.repository;

import football.start.allOfFootball.domain.Field;
import football.start.allOfFootball.jpaRepository.JpaFieldRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@RequiredArgsConstructor
@Repository
public class AdminRepositoryImpl implements AdminRepository{

    private final JpaFieldRepository jpaFieldRepository;
    @Override
    public void saveField(Field field) {
        jpaFieldRepository.save(field);
    }
}

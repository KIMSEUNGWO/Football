package football.manager.repository;

import football.common.domain.Manager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ManagerRepository {

    private final JpaManagerRepository jpaManagerRepository;

    public void save(Manager manager) {
        jpaManagerRepository.save(manager);
    }
}

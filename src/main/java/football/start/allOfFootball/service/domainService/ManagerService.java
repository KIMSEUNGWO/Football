package football.start.allOfFootball.service.domainService;

import football.start.allOfFootball.domain.Manager;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.repository.domainRepository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManagerService {

    private final ManagerRepository managerRepository;

    public void save(Member member) {
        Manager manager = Manager.builder().member(member).build();
        managerRepository.save(manager);
    }
}

package football.start.allOfFootball.service.domainService;

import football.internal.database.domain.Manager;
import football.internal.database.domain.Member;
import football.start.allOfFootball.dto.ManagerApplyDto;
import football.start.allOfFootball.repository.domainRepository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManagerService {

    private final ManagerRepository managerRepository;

    public void save(Member member, ManagerApplyDto data) {
        Manager manager = Manager.builder()
            .member(member)
            .name(data.getName())
            .region(data.getRegion())
            .build();
        managerRepository.save(manager);
    }
}

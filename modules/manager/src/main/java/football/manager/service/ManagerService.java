package football.manager.service;

import football.common.domain.Manager;
import football.common.domain.Member;
import football.manager.dto.ManagerApplyDto;
import football.manager.repository.ManagerRepository;
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

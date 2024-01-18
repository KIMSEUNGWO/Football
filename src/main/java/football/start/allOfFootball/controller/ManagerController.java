package football.start.allOfFootball.controller;

import football.start.allOfFootball.SessionConst;
import football.start.allOfFootball.customAnnotation.SessionLogin;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.dto.ManagerApplyDto;
import football.start.allOfFootball.repository.domainRepository.ManagerRepository;
import football.start.allOfFootball.service.domainService.ManagerService;
import football.start.allOfFootball.service.domainService.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ManagerController {

    private final MemberService memberService;
    private final ManagerService managerService;

    @GetMapping("/manager")
    public String manager() {

        return "manager";
    }
    @ResponseBody
    @PostMapping("/manager/apply/confirm")
    public ResponseEntity<String> apply(@SessionLogin Member member, @RequestBody ManagerApplyDto data) {
        managerService.save(member, data);
        return new ResponseEntity<>("연결됐다", HttpStatus.OK);
    }

}

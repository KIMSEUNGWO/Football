package football.start.allOfFootball.controller;

import football.start.allOfFootball.SessionConst;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.repository.domainRepository.ManagerRepository;
import football.start.allOfFootball.service.domainService.ManagerService;
import football.start.allOfFootball.service.domainService.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ManagerController {

    private final MemberService memberService;
    private final ManagerService managerService;

    @GetMapping("/manager/test")
    public String test(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Long memberId) {
        Optional<Member> byMemberId = memberService.findByMemberId(memberId);
        Member member = byMemberId.get();
        managerService.save(member);

        return "redirect:/";
    }
}

package football.start.allOfFootball.controller;

import football.start.allOfFootball.common.alert.AlertUtils;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.formatter.NumberFormatter;
import football.start.allOfFootball.service.domainService.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Optional;

import static football.start.allOfFootball.SessionConst.LOGIN_MEMBER;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CashController {

    private final MemberService memberService;

    @GetMapping("/cash/charge")
    public String cash(@SessionAttribute(name = LOGIN_MEMBER, required = false) Long memberId, HttpServletResponse response, Model model) {
        if (memberId == null) {
            return AlertUtils.alertAndMove(response, "로그인이 필요합니다.", "/login");
        }
        Optional<Member> findMember = memberService.findByMemberId(memberId);
        if (findMember.isEmpty()) {
            return AlertUtils.alertAndMove(response, "존재하지 않는 회원입니다.", "/");
        }

        Member member = findMember.get();

        model.addAttribute("cash", NumberFormatter.format(member.getMemberCash()));

        return "cash_charge";
    }
}

package football.start.allOfFootball.controller;

import football.start.allOfFootball.common.alert.AlertUtils;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.formatter.NumberFormatter;
import football.start.allOfFootball.service.domainService.MemberService;
import jakarta.servlet.http.HttpServletRequest;
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
    public String cash(@SessionAttribute(name = LOGIN_MEMBER, required = false) Long memberId, HttpServletResponse response, Model model, HttpServletRequest request) {
        Optional<Member> findMember = memberService.findByMemberId(memberId);
        Member member = findMember.get();

        String redirectURL = request.getHeader("referer");

        model.addAttribute("redirect", redirectURL);
        model.addAttribute("cash", NumberFormatter.format(member.getMemberCash()));

        return "cash_charge";
    }
}

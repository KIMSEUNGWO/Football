package football.start.allOfFootball.controller;

import football.common.config.auth.PrincipalDetails;
import football.common.domain.Member;
import football.common.formatter.NumberFormatter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CashController {


    @GetMapping("/cash/charge")
    public String cash(@AuthenticationPrincipal PrincipalDetails user, Model model, HttpServletRequest request) {
        Member member = user.getMember();
        String redirectURL = request.getHeader("referer");

        model.addAttribute("redirect", redirectURL);
        model.addAttribute("cash", NumberFormatter.format(member.getMemberCash()));
        return "cash_charge";
    }
}

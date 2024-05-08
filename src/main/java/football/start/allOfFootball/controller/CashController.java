package football.start.allOfFootball.controller;

import football.start.allOfFootball.customAnnotation.SessionLogin;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.formatter.NumberFormatter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CashController {


    @GetMapping("/cash/charge")
    public String cash(@SessionLogin Member member, Model model, HttpServletRequest request) {
        String redirectURL = request.getHeader("referer");

        model.addAttribute("redirect", redirectURL);
        model.addAttribute("cash", NumberFormatter.format(member.getMemberCash()));

        return "cash_charge";
    }
}

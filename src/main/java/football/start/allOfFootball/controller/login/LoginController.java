package football.start.allOfFootball.controller.login;

import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

import static football.start.allOfFootball.SessionConst.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    public final LoginService loginService;


    @GetMapping
    public String startLogin(@SessionAttribute(name = LOGIN_MEMBER, required = false) Long memberId) {
        if (memberId != null) return "redirect:/";
        return "login";
    }


    @PostMapping
    public String loginAction(@SessionAttribute(name = LOGIN_MEMBER, required = false) Long memberId,
                              LoginDto loginDto,
                              RedirectAttributes redirectAttributes,
                              HttpSession session,
                              HttpServletRequest request) {

        if (memberId != null) return "redirect:/";
        if (loginDto == null || loginDto.getEmail() == null || loginDto.getPassword() == null) return "redirect:/";



        Optional<Member> loginMember = loginService.login(loginDto.getEmail(), loginDto.getPassword());
        if (loginMember.isEmpty()) {
            redirectAttributes.addFlashAttribute("loginEmail", loginDto.getEmail());
            return "redirect:/login";
        }
        String redirectURL = request.getParameter(REDIRECT_URL);

        // 세션 생성
        session.setAttribute(LOGIN_MEMBER, loginMember.get().getMemberId());

        return "redirect:" + redirectURL;
    }


}

package football.start.allOfFootball.controller.login;

import football.start.allOfFootball.controller.api.kakaoLogin.KakaoLoginService;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.domain.Social;
import football.start.allOfFootball.enums.SocialEnum;
import football.start.allOfFootball.service.LoginService;
import football.start.allOfFootball.service.domainService.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

import static football.start.allOfFootball.SessionConst.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;
    public final LoginService loginService;
    public final KakaoLoginService kakaoLoginService;


    @GetMapping("/login")
    public String startLogin(@SessionAttribute(name = LOGIN_MEMBER, required = false) Long memberId, @ModelAttribute LoginDto loginDto) {
        if (memberId != null) return "redirect:/";
        return "/login/login";
    }


    @PostMapping("/login")
    public String loginAction(@SessionAttribute(name = LOGIN_MEMBER, required = false) Long memberId,
                              @SessionAttribute(name = REDIRECT_URL, required = false) String redirectURI,
                              @ModelAttribute LoginDto loginDto,
                              HttpSession session,
                              Model model) {
        if (memberId != null) return "redirect:/";
        if (loginDto == null || loginDto.getEmail() == null || loginDto.getPassword() == null) return "redirect:/";

        Optional<Member> loginMember = loginService.login(loginDto.getEmail(), loginDto.getPassword());
        if (loginMember.isEmpty()) {
            model.addAttribute("errorMsg", "이메일 또는 비밀번호가 일치하지 않습니다.");
            return "/login/login";
        }
        Member findMember = loginMember.get();
        // 세션 생성
        session.setAttribute(LOGIN_MEMBER, findMember.getMemberId());
        session.removeAttribute(REDIRECT_URL);
        if (redirectURI == null) {
            return "redirect:";
        }
        return "redirect:" + redirectURI;
    }

    @GetMapping("/logout")
    public String logout(@SessionAttribute(name = LOGIN_MEMBER, required = false) Long memberId, HttpServletRequest request) {
        if (memberId == null) return "redirect:/";

        HttpSession session = request.getSession();
        session.removeAttribute(LOGIN_MEMBER);

        Optional<Member> byMemberId = memberService.findByMemberId(memberId);
        if (byMemberId.isEmpty()) return "redirect:/";

        // 소셜 로그아웃
        Member member = byMemberId.get();
        Social social = member.getSocial();
        if (social == null) return "redirect:/";

        SocialEnum type = social.getSocialType();
        if (type == SocialEnum.KAKAO) {
            kakaoLoginService.logout(social.getKakaoToken());
        }

        return "redirect:/";
    }
}

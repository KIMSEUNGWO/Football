package football.start.allOfFootball.controller.login;

import football.start.allOfFootball.controller.api.smsAPI.SmsRequest;
import football.start.allOfFootball.controller.api.smsAPI.SmsService;
import football.start.allOfFootball.controller.api.smsAPI.exception.CertificationException;
import football.start.allOfFootball.customAnnotation.SessionLogin;
import football.start.allOfFootball.domain.Admin;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.domain.Social;
import football.start.allOfFootball.dto.json.FindEmail;
import football.start.allOfFootball.dto.json.JsonDefault;
import football.start.allOfFootball.enums.SocialEnum;
import football.start.allOfFootball.service.AdminService;
import football.start.allOfFootball.service.LoginService;
import football.start.allOfFootball.service.domainService.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static football.start.allOfFootball.SessionConst.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;
    private final LoginService loginService;
    private final AdminService adminService;
    private final SmsService smsService;



    @GetMapping("/login")
    public String startLogin(@SessionLogin Member member,
                             @ModelAttribute LoginDto loginDto,
                             @RequestParam(required = false) String url,
                             Model model) {
        if (member != null) return "redirect:/";

        if (url != null && !url.equals("null")) {
            model.addAttribute("url", url);
        }
        return "/login/login";
    }


    @PostMapping("/login")
    public String loginAction(@Validated @ModelAttribute LoginDto loginDto,
                              BindingResult bindingResult,
                              @RequestParam(required = false) String url,
                              HttpSession session,
                              Model model) {
        String redirectUrl = getRedirectUrl(url);

        if (bindingResult.hasErrors()) {
            log.info("errors={} ", bindingResult);
            return "/login/login";
        }

        Optional<Member> loginMember = loginService.login(loginDto.getEmail(), loginDto.getPassword());
        if (loginMember.isEmpty()) {
            bindingResult.reject("loginReject");
            return "/login/login";
        }
        Member findMember = loginMember.get();

        session.setAttribute(LOGIN_MEMBER, findMember.getMemberId());

        Optional<Admin> byMember = adminService.findByMember(findMember);
        if (byMember.isPresent()) {
            return "redirect:/admin/ground";
        }
        return "redirect:" + redirectUrl;
    }

    private String getRedirectUrl(String url) {
        if (url == null || url.equals("null")) return "/";
        return url;
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
            return "redirect:/logout/kakao/" + memberId;
        }

        return "redirect:/";
    }

    @GetMapping("/findEmail")
    public String findEmail() {

        return "/login/findEmail";
    }

    @ResponseBody
    @PostMapping("/findEmail")
    public ResponseEntity<JsonDefault> postEmail(@RequestBody SmsRequest smsRequest) {

        smsService.isValidFind(smsRequest);

        Optional<Member> findEmail = memberService.findByMemberPhone(smsRequest.getPhone());
        if (findEmail.isEmpty()) {
            return new ResponseEntity<>(new FindEmail("ok", "가입 이력이 존재하지 않습니다.", null, null), HttpStatus.OK);
        }
        Member member = findEmail.get();
        Social social = member.getSocial();
        return new ResponseEntity<>(new FindEmail("ok", "", (social != null) ? social.getSocialType() : null, member.getMemberEmail()), HttpStatus.OK);
    }

    @GetMapping("/findPassword")
    public String findPassword() {

        return "/login/findPassword";
    }

    @ResponseBody
    @PostMapping("/findPassword")
    public ResponseEntity<JsonDefault> postPassword(@RequestBody SmsRequest smsRequest) {

        smsService.checkCertificationFind(smsRequest);

        Optional<Member> findEmail = memberService.findByMemberEmailAndMemberPhone(smsRequest.getEmail(), smsRequest.getPhone());
        if (findEmail.isEmpty()) {
            return new ResponseEntity<>(new JsonDefault("error", "일치하는 회원정보가 없습니다."), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new JsonDefault("ok", ""), HttpStatus.OK);
    }

    @Transactional
    @ResponseBody
    @PostMapping("/changePassword")
    public ResponseEntity<JsonDefault> postPassword(@RequestBody FindPassword findPassword) {

        smsService.checkCertificationFind(findPassword);

        Optional<Member> findMember = memberService.findByMemberEmailAndMemberPhone(findPassword.getEmail(), findPassword.getPhone());
        if (findMember.isEmpty()) {
            return new ResponseEntity<>(new JsonDefault("error", "일치하는 회원정보가 없습니다."), HttpStatus.BAD_REQUEST);
        }
        Member member = findMember.get();
        if (member.getSocial() != null) {
            return new ResponseEntity<>(new JsonDefault("error", "소셜로 가입한 회원입니다."), HttpStatus.BAD_REQUEST);
        }
        String password = findPassword.getPassword();
        String passwordCheck = findPassword.getPasswordCheck();
        if (!password.equals(passwordCheck)) {
            return new ResponseEntity<>(new JsonDefault("error", "변경할 비밀번호가 일치하지 않습니다."), HttpStatus.OK);
        }
        smsService.isValidFind(findPassword);
        memberService.changePassword(member, password);

        return new ResponseEntity<>(new JsonDefault("ok", ""), HttpStatus.OK);
    }
}

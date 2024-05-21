package football.login.controller;

import football.api.sms.dto.SmsRequest;
import football.api.sms.exception.CertificationException;
import football.api.sms.service.SmsService;
import football.common.domain.Member;
import football.common.jpaRepository.JpaAdminRepository;
import football.common.service.MemberService;
import football.login.dto.FindEmail;
import football.login.dto.FindPassword;
import football.login.dto.LoginDto;
import football.login.service.LoginService;
import football.common.dto.json.JsonDefault;
import football.common.enums.SocialEnum;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static football.common.consts.Constant.*;
import static football.common.consts.SessionConst.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;
    private final LoginService loginService;
    private final JpaAdminRepository adminService;
    private final SmsService smsService;


    @GetMapping("/login")
    public String startLogin(@SessionAttribute(name = LOGIN_MEMBER, required = false) Long memberId,
                             @ModelAttribute LoginDto loginDto,
                             @RequestParam(required = false) String url,
                             Model model) {
        if (memberId != null) return "redirect:/";

        if (url != null && !url.equals("null")) model.addAttribute("url", url);
        return "/login/login";
    }


    @PostMapping("/login")
    public String loginAction(@Validated @ModelAttribute LoginDto loginDto,
                              BindingResult bindingResult,
                              @RequestParam(required = false) String url,
                              HttpSession session) {

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

        // TODO
        // adminService.isAdmin(findMember) 로 다시 변경해야함
        if (adminService.existsByMember(findMember)) {
            return "redirect:/admin/ground";
        }
        return "redirect:" + getRedirectUrl(url);
    }

    private String getRedirectUrl(String url) {
        if (url == null || url.equals("null")) return "/";
        return url;
    }

    @GetMapping("/logout")
    public String logout(@SessionAttribute(name = LOGIN_MEMBER, required = false) Long memberId, HttpSession session) {
        if (memberId == null) return "redirect:/";

        session.removeAttribute(LOGIN_MEMBER);

        Optional<Member> byMemberId = memberService.findByMemberId(memberId);
        if (byMemberId.isEmpty()) return "redirect:/";

        // 소셜 로그아웃
        Member member = byMemberId.get();

        if (member.isSocial()) {
            if (member.socialTypeIs(SocialEnum.KAKAO)) {
                return "redirect:/logout/kakao/" + memberId;
            }
        }

        return "redirect:/";
    }

    @GetMapping("/findEmail")
    public String findEmail() {
        return "/login/findEmail";
    }

    @ResponseBody
    @PostMapping("/findEmail")
    public ResponseEntity<JsonDefault> postEmail(@RequestBody SmsRequest smsRequest) throws CertificationException {

        smsService.isValidFind(smsRequest.getPhone(), smsRequest.getCertificationNumber());

        Optional<Member> findEmail = memberService.findByMemberPhone(smsRequest.getPhone());
        if (findEmail.isEmpty()) {
            return ResponseEntity.ok(new FindEmail(OK, "가입 이력이 존재하지 않습니다.", null, null));
        }
        Member member = findEmail.get();
        return ResponseEntity.ok(new FindEmail(OK, "", member.isSocial() ? member.getSocialType() : null, member.getMemberEmail()));
    }

    @GetMapping("/findPassword")
    public String findPassword() {
        return "/login/findPassword";
    }

    @ResponseBody
    @PostMapping("/findPassword")
    public ResponseEntity<JsonDefault> postPassword(@RequestBody SmsRequest smsRequest) throws CertificationException {

        smsService.checkCertificationFind(smsRequest);
        boolean exists = memberService.existsByEmailAndPhone(smsRequest.getEmail(), smsRequest.getPhone());
        return ResponseEntity.ok(
            exists ?    new JsonDefault(OK, "")
                   :    new JsonDefault(ERROR, "일치하는 회원정보가 없습니다."));
    }

    @Transactional
    @ResponseBody
    @PostMapping("/changePassword")
    public ResponseEntity<JsonDefault> postPassword(@RequestBody FindPassword findPassword) throws CertificationException {

        smsService.checkCertificationFind(findPassword);

        Optional<Member> findMember = memberService.findByMemberEmailAndMemberPhone(findPassword.getEmail(), findPassword.getPhone());
        if (findMember.isEmpty()) {
            return ResponseEntity.badRequest().body(new JsonDefault(ERROR, "일치하는 회원정보가 없습니다."));
        }
        Member member = findMember.get();

        if (member.isSocial()) {
            return ResponseEntity.badRequest().body(new JsonDefault(ERROR, "소셜로 가입한 회원입니다."));
        }
        String password = findPassword.getPassword();
        String passwordCheck = findPassword.getPasswordCheck();
        if (!password.equals(passwordCheck)) {
            return ResponseEntity.badRequest().body(new JsonDefault(ERROR, "비밀번호가 일치하지 않습니다."));
        }
        smsService.isValidFind(findPassword.getPhone(), findPassword.getCertificationNumber());
        memberService.changePassword(member, password);

        return ResponseEntity.ok(new JsonDefault(OK, ""));
    }
}

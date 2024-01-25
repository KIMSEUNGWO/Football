package football.start.allOfFootball.controller.login;

import football.start.allOfFootball.common.MatchTeamAlgorithms;
import football.start.allOfFootball.common.alert.AlertUtils;
import football.start.allOfFootball.domain.Match;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.domain.Orders;
import football.start.allOfFootball.enums.GenderEnum;
import football.start.allOfFootball.enums.TeamEnum;
import football.start.allOfFootball.enums.gradeEnums.GradeEnum;
import football.start.allOfFootball.service.OrderService;
import football.start.allOfFootball.service.RegisterService;
import football.start.allOfFootball.service.domainService.MatchService;
import football.start.allOfFootball.service.domainService.MemberService;
import football.start.allOfFootball.validator.RegisterValidator;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static football.start.allOfFootball.SessionConst.*;
import static football.start.allOfFootball.enums.matchEnums.MatchStatus.경기시작전;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegisterController {

    private final RegisterService registerService;

    private final RegisterValidator registerValidator;

    @InitBinder
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(registerValidator);
    }

    @GetMapping
    public String registerPage(@ModelAttribute RegisterDto registerDto, HttpSession session) {
        // 회원가입 세션 생성
        session.setAttribute(REGISTER, REGISTER);
        return "/login/register";
    }

    @PostMapping
    public String registerAction(@Validated @ModelAttribute RegisterDto registerDto, BindingResult bindingResult, HttpSession httpSession, HttpServletResponse response) {
        Object session = httpSession.getAttribute(REGISTER); // 회원가입 세션 get
        if (session == null) {
            AlertUtils.alert(response, "회원가입 세션이 만료되었습니다.");
            return "redirect:/";
        }
        System.out.println("registerDto = " + registerDto);
        if (!registerDto.getPassword().equals(registerDto.getPasswordCheck())) {
            bindingResult.rejectValue("passwordCheck", null, "비밀번호가 일치하지 않습니다.");
        }
        if (bindingResult.hasFieldErrors("phoneCheck")) {
            return AlertUtils.alertAndMove(response, bindingResult.getFieldError("phoneCheck").getDefaultMessage(), "/register");
        }
        if (bindingResult.hasErrors()) {
            System.out.println("bindingResult = " + bindingResult);
            return "/login/register";
        }
        boolean distinct = registerService.distinctEmail(registerDto.getEmail());
        if (distinct) {
            bindingResult.rejectValue("email", null, "중복된 이메일입니다.");
            return "/login/register";
        }
        Member saveMember = registerDto.builder();
        registerService.save(saveMember);
        httpSession.removeAttribute(REGISTER); // DB 저장 완료 후 회원가입 세션 삭제
        return AlertUtils.alertAndMove(response, "회원가입이 완료되었습니다.", "/login");
    }


}

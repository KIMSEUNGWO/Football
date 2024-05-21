package football.login.controller;

import football.common.domain.Member;
import football.common.common.alert.AlertUtils;
import football.login.dto.RegisterDto;
import football.login.service.RegisterService;
import football.login.validator.RegisterValidator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import static football.common.consts.SessionConst.*;

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
    public String registerPage(@ModelAttribute RegisterDto registerDto, HttpServletResponse response) {
        // 회원가입 쿠키 생성
        Cookie cookie = new Cookie(REGISTER, REGISTER);

        cookie.setMaxAge(60 * 10); // 쿠키 유효기간: 10분
        cookie.setHttpOnly(true); // HttpOnly 속성 설정
        cookie.setPath("/register");

        response.addCookie(cookie);
        return "/login/register";
    }

    @PostMapping
    public String registerAction(@Validated @ModelAttribute RegisterDto registerDto, BindingResult bindingResult,
                                 @CookieValue(name = REGISTER, required = false) String registerCookie,
                                 HttpServletResponse response) {
        if (registerCookie == null) {
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
            return "redirect:/login/register";
        }
        boolean distinct = registerService.distinctEmail(registerDto.getEmail());
        if (distinct) {
            bindingResult.rejectValue("email", null, "중복된 이메일입니다.");
            return "/login/register";
        }
        Member saveMember = registerDto.builder();
        registerService.save(saveMember);

        Cookie cookie = new Cookie(REGISTER, REGISTER);
        cookie.setMaxAge(0); // 쿠키 유효기간: 10분
        cookie.setHttpOnly(true); // HttpOnly 속성 설정
        cookie.setPath("/register");
        response.addCookie(cookie); // DB 저장 완료 후 회원가입 쿠키 삭제

        return AlertUtils.alertAndMove(response, "회원가입이 완료되었습니다.", "/login");
    }

}

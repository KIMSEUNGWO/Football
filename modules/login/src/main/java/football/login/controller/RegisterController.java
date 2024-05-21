package football.login.controller;

import football.common.domain.Member;
import football.common.common.alert.AlertUtils;
import football.login.dto.RegisterDto;
import football.login.service.RegisterService;
import football.login.validator.RegisterValidator;
import jakarta.servlet.http.HttpServletResponse;
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
        response.addCookie(registerService.createCertCookie());
        return "/login/register";
    }

    @PostMapping
    public String registerAction(@Validated @ModelAttribute RegisterDto registerDto, BindingResult bindingResult,
                                 @CookieValue(name = REGISTER, required = false) String registerCookie,
                                 HttpServletResponse response) {
        if (registerCookie == null) {
            AlertUtils.alert(response, "회원가입 세션이 만료되었습니다. 다시 시도해주세요.");
            return "redirect:/";
        }
        System.out.println("registerDto = " + registerDto);

        if (bindingResult.hasFieldErrors("phoneCheck")) {
            return AlertUtils.alertAndMove(response, bindingResult.getFieldError("phoneCheck").getDefaultMessage(), "/register");
        }
        if (bindingResult.hasErrors()) {
            System.out.println("bindingResult = " + bindingResult);
            return "/login/register";
        }
        Member saveMember = registerDto.builder();
        registerService.save(saveMember);

        response.addCookie(registerService.removeCertCookie()); // DB 저장 완료 후 회원가입 쿠키 삭제

        return AlertUtils.alertAndMove(response, "회원가입이 완료되었습니다.", "/login");
    }

}

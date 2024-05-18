package football.start.allOfFootball.controller.login;

import football.common.domain.Match;
import football.common.domain.Member;
import football.common.domain.Orders;
import football.common.common.alert.AlertUtils;
import football.common.exception.match.NotExistsMatchException;
import football.start.allOfFootball.service.OrderService;
import football.start.allOfFootball.service.RegisterService;
import football.start.allOfFootball.service.domainService.MatchService;
import football.start.allOfFootball.service.domainService.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static football.common.consts.SessionConst.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegisterController {

    private final RegisterService registerService;

    private final RegisterValidator registerValidator;

    private final MemberService memberService;
    private final MatchService matchService;
    private final OrderService orderService;

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

    @GetMapping("/exData/member")
    public String exDataMember() {
        String[] names = {"김영","김일","김이","김삼","김사","김오","김육","김칠","김팔","김구","김십","박영","박일","박이","박삼","박사","박오","박육","박칠","박팔","박구","박십"};
        for (int i = 0; i < 18; i++) {
            RegisterDto registerDto = getRegisterDto(i, names[i]);
            Member member = registerDto.builder();
            member.setMemberCash(30000);

            registerService.save(member);

        }
        return "main";
    }

    @GetMapping("/exData/match/{matchId}/{startMemberId}/{count}")
    public String exDateMatchMember(@PathVariable(name = "matchId") Long matchId, @PathVariable(name = "startMemberId") Integer startNumber, @PathVariable(name = "count") Integer count) throws NotExistsMatchException {
        for (int i = startNumber; i <= startNumber + count; i++) {
            Optional<Member> byMemberId = memberService.findByMemberId((long) i);
            if (byMemberId.isEmpty()) break;

            Member member = byMemberId.get();

            Match match = matchService.findByMatch(matchId, "/");

            List<Orders> ordersList = member.getOrdersList();


            int price = 10000;

            Orders orders = Orders.builder()
                .match(match)
                .member(member)
                .payment(price)
                .build();

            orderService.save(orders, member, Optional.empty(), price); // order 저장
            matchService.refreshMatchStatus(match); // MatchStatus 상태 변경

            log.info("Orders 정상 처리");

        }

        return "main";
    }


    private RegisterDto getRegisterDto(int index, String name) {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setEmail("ex" + 1 + "@naver.com");
        registerDto.setPassword("!@#QWEasd1");
        registerDto.setPasswordCheck("!@#QWEasd1");
        registerDto.setName(name);
        registerDto.setGender("남자");
        registerDto.setBirthday("990101");
        registerDto.setPhone("010-12" + index + "-5678");
        return registerDto;
    }


}

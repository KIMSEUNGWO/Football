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
    private final MatchService matchService;
    private final OrderService orderService;
    private final MemberService memberService;

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

    @GetMapping("/member/create")
    public String test2() {
        Member member = Member.builder().memberEmail("t1@n.com").memberPassword("!@#QWEasd1").memberCash(30000).memberName("김김김").grade(GradeEnum.루키).memberGender(GenderEnum.남자).memberBirthday(LocalDate.now()).memberPhone("01066038635").build();
        Member member2 = Member.builder().memberEmail("t2@n.com").memberPassword("!@#QWEasd1").memberCash(30000).memberName("승승승").grade(GradeEnum.루키).memberGender(GenderEnum.남자).memberBirthday(LocalDate.now()).memberPhone("01066038635").build();
        Member member3 = Member.builder().memberEmail("t3@n.com").memberPassword("!@#QWEasd1").memberCash(30000).memberName("우우우").grade(GradeEnum.루키).memberGender(GenderEnum.남자).memberBirthday(LocalDate.now()).memberPhone("01066038635").build();
        Member member4 = Member.builder().memberEmail("t4@n.com").memberPassword("!@#QWEasd1").memberCash(30000).memberName("김김승승우우").grade(GradeEnum.루키).memberGender(GenderEnum.남자).memberBirthday(LocalDate.now()).memberPhone("01066038635").build();
        Member member5 = Member.builder().memberEmail("t5@n.com").memberPassword("!@#QWEasd1").memberCash(30000).memberName("우승김").grade(GradeEnum.루키).memberGender(GenderEnum.남자).memberBirthday(LocalDate.now()).memberPhone("01066038635").build();

        registerService.save(member);
        registerService.save(member2);
        registerService.save(member3);
        registerService.save(member4);
        registerService.save(member5);

        return "redirect:/";
    }

    @GetMapping("w/{matchId}")
    public String test4(@PathVariable Long matchId) {
        Optional<Match> byMatch = matchService.findByMatch(matchId);
        Match match = byMatch.get();

        Member member = memberService.findByMemberId(7L).get();
        Member member2 = memberService.findByMemberId(3L).get();
        Member member3 = memberService.findByMemberId(4L).get();
        Member member4 = memberService.findByMemberId(5L).get();
        Member member5 = memberService.findByMemberId(6L).get();


        Orders orders = Orders.build(match, member);
        orderService.save(orders, member, Optional.empty(), 10000); // order 저장
        matchService.refreshMatchStatus(match); // MatchStatus 상태 변경

        Orders orders2 = Orders.build(match, member2);
        orderService.save(orders2, member2, Optional.empty(), 10000); // order 저장
        matchService.refreshMatchStatus(match); // MatchStatus 상태 변경

        Orders orders3 = Orders.build(match, member3);
        orderService.save(orders3, member3, Optional.empty(), 10000); // order 저장
        matchService.refreshMatchStatus(match); // MatchStatus 상태 변경

        Orders orders4 = Orders.build(match, member4);
        orderService.save(orders4, member4, Optional.empty(), 10000); // order 저장
        matchService.refreshMatchStatus(match); // MatchStatus 상태 변경

        Orders orders5 = Orders.build(match, member5);
        orderService.save(orders5, member5, Optional.empty(), 10000); // order 저장
        matchService.refreshMatchStatus(match); // MatchStatus 상태 변경

        return "redirect:/";
    }
    @GetMapping("/match/start/{matchId}")
    public String test3(@PathVariable Long matchId) {
        Optional<Match> byMatch = matchService.findByMatch(matchId);
        Match match = byMatch.get();
        match.setMatchStatus(경기시작전);
        List<Orders> ordersList = match.getOrdersList();

        // team 자동 분배 알고리즘 시작
        MatchTeamAlgorithms setTeam = new MatchTeamAlgorithms(ordersList);
        Map<TeamEnum, List<Orders>> result = setTeam.getResult(match.getMatchCount());

        // 결과 Orders TeamEnum 설정
        orderService.setTeam(result);

        return "redirect:/";
    }

}

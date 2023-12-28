package football.start.allOfFootball.controller.mypage;

import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.dto.CouponListForm;
import football.start.allOfFootball.service.MypageService;
import football.start.allOfFootball.service.domainService.CouponListService;
import football.start.allOfFootball.service.domainService.MatchService;
import football.start.allOfFootball.service.domainService.MemberService;
import football.start.allOfFootball.service.domainService.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static football.start.allOfFootball.SessionConst.*;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/mypage")
public class MypageController {

    private final MypageService mypageService;
    private final CouponListService couponListService;
    private final PaymentService paymentService;
    private final MemberService memberService;

    @GetMapping
    public String mainPage(@SessionAttribute(name = LOGIN_MEMBER, required = false) Long memberId, Model model) {
        Optional<Member> optionalMember = memberService.findByMemberId(memberId);
        if (optionalMember.isEmpty()) {
            log.info("사용자 정보가 없습니다");
            return "redirect:/";
        }
        Member findMember = optionalMember.get();
        MypageMainDto mypageMainDto = mypageService.getMypageMain(findMember);
        model.addAttribute("main", mypageMainDto);
        return "/mypage/mypage_main";
    }

    @GetMapping("/order")
    public String orderList(@SessionAttribute(name = LOGIN_MEMBER, required = false) Long memberId, Model model) {
        Optional<Member> optionalMember = memberService.findByMemberId(memberId);
        if (optionalMember.isEmpty()) {
            log.info("사용자 정보가 없습니다");
            return "redirect:/";
        }
        return "/mypage/mypage_order";
    }
    @GetMapping("/cash")
    public String cashList(@SessionAttribute(name = LOGIN_MEMBER, required = false) Long memberId, Model model) {
        Optional<Member> optionalMember = memberService.findByMemberId(memberId);
        if (optionalMember.isEmpty()) {
            log.info("사용자 정보가 없습니다");
            return "redirect:/";
        }
        Member findMember = optionalMember.get();

        List<CashListForm> cashList = paymentService.findByAllMemberCashList(findMember);
        model.addAttribute("cashList", cashList);
        return "/mypage/mypage_cash";
    }
    @GetMapping("/coupon")
    public String couponList(@SessionAttribute(name = LOGIN_MEMBER, required = false) Long memberId, Model model) {
        Optional<Member> optionalMember = memberService.findByMemberId(memberId);
        if (optionalMember.isEmpty()) {
            log.info("사용자 정보가 없습니다");
            return "redirect:/";
        }
        Member findMember = optionalMember.get();
        List<CouponListForm> couponList = couponListService.getCouponList(findMember);
        model.addAttribute("couponList", couponList);
        return "/mypage/mypage_coupon";
    }

    @GetMapping("/manager")
    public String manager(@SessionAttribute(name = LOGIN_MEMBER, required = false) Long memberId, Model model) {
        Optional<Member> optionalMember = memberService.findByMemberId(memberId);
        if (optionalMember.isEmpty()) {
            log.info("사용자 정보가 없습니다");
            return "redirect:/";
        }
        Member findMember = optionalMember.get();
        Map<String, List<ManagerDataForm>> list = mypageService.getManagerList(findMember);

        model.addAttribute("matchList", list);
        return "/mypage/mypage_manager";
    }

}

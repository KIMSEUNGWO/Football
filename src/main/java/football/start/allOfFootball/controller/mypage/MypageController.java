package football.start.allOfFootball.controller.mypage;

import football.common.domain.Member;
import football.payment.dto.CashListForm;
import football.payment.service.PaymentService;
import football.start.allOfFootball.dto.CouponListForm;
import football.start.allOfFootball.service.MypageService;
import football.start.allOfFootball.service.domainService.CouponListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/mypage")
public class MypageController {

    private final MypageService mypageService;
    private final CouponListService couponListService;
    private final PaymentService paymentService;

    @GetMapping
    public String mainPage(Model model) {
        Member member = (Member) model.getAttribute("member");

        MypageMainDto mypageMainDto = mypageService.getMypageMain(member);
        model.addAttribute("main", mypageMainDto);
        return "/mypage/mypage_main";
    }

    @GetMapping("/order")
    public String orderList(Model model) {

        return "/mypage/mypage_order";
    }
    @GetMapping("/cash")
    public String cashList(Model model) {
        Member member = (Member) model.getAttribute("member");

        List<CashListForm> cashList = paymentService.findByAllMemberCacheList(member);
        model.addAttribute("cashList", cashList);
        return "/mypage/mypage_cash";
    }
    @GetMapping("/coupon")
    public String couponList(Model model) {
        Member member = (Member) model.getAttribute("member");

        List<CouponListForm> couponList = couponListService.getCouponList(member);
        model.addAttribute("couponList", couponList);
        return "/mypage/mypage_coupon";
    }

    @GetMapping("/manager")
    public String manager(Model model) {
        Member member = (Member) model.getAttribute("member");
        if (member.getManager() == null) return "redirect:/mypage";

        Map<String, List<ManagerDataForm>> list = mypageService.getManagerList(member);
        model.addAttribute("matchList", list);
        return "/mypage/mypage_manager";
    }

}

package football.start.allOfFootball.controller.mypage;

import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.service.MypageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Optional;

import static football.start.allOfFootball.SessionConst.*;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/mypage")
public class MypageController {

    private final MypageService mypageService;

    @GetMapping
    public String mainPage(@SessionAttribute(name = LOGIN_MEMBER, required = false) Long memberId, Model model) {
        Optional<Member> optionalMember = mypageService.findById(memberId);
        if (optionalMember.isEmpty()) {
            log.info("사용자 정보가 없습니다");
            return "redirect:/";
        }
        Member findMember = optionalMember.get();
        MyProfileDto myProfileDto = mypageService.getMyProfile(findMember);
        model.addAttribute("profile", myProfileDto);
        return "/mypage/mypage_main";
    }

    @GetMapping("/order")
    public String orderList(@SessionAttribute(name = LOGIN_MEMBER, required = false) Long memberId) {


        return "/mypage/mypage_order";
    }
    @GetMapping("/cash")
    public String cashList(@SessionAttribute(name = LOGIN_MEMBER, required = false) Long memberId) {


        return "/mypage/mypage_cash";
    }
    @GetMapping("/coupon")
    public String couponList(@SessionAttribute(name = LOGIN_MEMBER, required = false) Long memberId) {


        return "/mypage/mypage_coupon";
    }

}

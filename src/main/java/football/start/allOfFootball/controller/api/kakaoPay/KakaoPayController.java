package football.start.allOfFootball.controller.api.kakaoPay;

import football.start.allOfFootball.SessionConst;
import football.start.allOfFootball.common.alert.AlertUtils;
import football.start.allOfFootball.controller.api.kakaoPay.dto.ApproveResponse;
import football.start.allOfFootball.controller.api.kakaoPay.dto.ReadyResponse;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.domain.Payment;
import football.start.allOfFootball.enums.paymentEnums.CacheEnum;
import football.start.allOfFootball.enums.paymentEnums.PaymentType;
import football.start.allOfFootball.service.domainService.MemberService;
import football.start.allOfFootball.service.domainService.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static football.start.allOfFootball.SessionConst.*;
import static football.start.allOfFootball.SessionConst.LOGIN_MEMBER;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/cache/charge/kakao")
public class KakaoPayController {

    private final KakaoPayService kakaoPayService;
    private final MemberService memberService;
    private final PaymentService paymentService;

    @PostMapping
    public @ResponseBody ReadyResponse requestPay(@SessionAttribute(name = LOGIN_MEMBER, required = false) Long memberId, @RequestBody KakaoPayDto kakaoPayDto, HttpServletRequest request) {
        String partner_order_id = memberId + "cache";

        ReadyResponse response = kakaoPayService.payReady(memberId, kakaoPayDto, partner_order_id);

        // 검증을 위한 tid 값 저장
        kakaoPayService.saveSessionTid(request, response, partner_order_id, memberId);

        return response;
    }

    @Transactional
    @GetMapping("/completed")
    public String payCompleted(@RequestParam("pg_token") String pg_token,
                               @SessionAttribute(name = TID, required = false) String tid,
                               @SessionAttribute(name = KAKAO_ORDER_ID, required = false) String partner_order_id,
                               @SessionAttribute(name = KAKAO_MEMBER_ID, required = false) Long kakaoMemberId,
                               @SessionAttribute(name = LOGIN_MEMBER, required = false) Long memberId,
                               HttpServletResponse response,
                               HttpServletRequest request,
                               Model model) {
        if (tid == null || partner_order_id == null || kakaoMemberId == null || memberId == null || kakaoMemberId != memberId) {
            return AlertUtils.alertAndMove(response, "잘못된 결제요청입니다.", "/");
        }
        log.info("결제승인 요청을 인증하는 토큰 : {}", pg_token);
        log.info("주문정보 : {}", partner_order_id);
        log.info("결재고유 번호 : {}", tid);

        Optional<Member> findMember = memberService.findByMemberId(memberId);
        if (findMember.isEmpty()) {
            return AlertUtils.alertAndMove(response, "잘못된 결제요청입니다.", "/");
        }

        ApproveResponse approve = kakaoPayService.payApprove(tid, pg_token, partner_order_id, memberId);

        Member member = findMember.get();
        Payment payment = Payment.builder()
            .member(member)
            .charge(approve.getAmount().getTotal())
            .resultCash(member.getMemberCache() + approve.getAmount().getTotal())
            .cacheType(CacheEnum.충전)
            .paymentType(PaymentType.카카오페이)
            .build();
        paymentService.save(payment);

        HttpSession session = request.getSession();
        String requestURL = (String) session.getAttribute(REDIRECT_URL);
        kakaoPayService.deleteSessionId(session);

        return "redirect:" + requestURL;
    }

    @GetMapping("/cancel")
    public String payCancel() {

        return "redirect:/";
    }

    @GetMapping("/fail")
    public String payFail() {

        return "redirect:/";
    }
}

package football.api.kakaopay.controller;

import football.api.kakaopay.dto.KakaoPayDto;
import football.api.kakaopay.service.KakaoPayService;
import football.common.common.alert.AlertTemplate;
import football.api.kakaopay.dto.ApproveResponse;
import football.api.kakaopay.dto.ReadyResponse;
import football.common.common.alert.AlertUtils;
import football.common.config.auth.PrincipalDetails;
import football.common.domain.Member;
import football.common.domain.Payment;
import football.common.enums.paymentEnums.CashEnum;
import football.common.enums.paymentEnums.PaymentType;
import football.payment.service.PaymentService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;

import static football.api.kakaopay.consts.KakaoPayConst.*;
import static football.api.kakaopay.consts.KakaoPayConst.KAKAO_ORDER_ID;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/cash/charge/kakao")
public class KakaoPayController {

    private final KakaoPayService kakaoPayService;
    private final PaymentService paymentService;

    @PostMapping
    public @ResponseBody ReadyResponse requestPay(@AuthenticationPrincipal PrincipalDetails user,
                                                  @RequestBody KakaoPayDto kakaoPayDto,
                                                  HttpSession session) {
        Member member = user.getMember();
        Long memberId = member.getMemberId();
        String partner_order_id = memberId + "cash";

        ReadyResponse response = kakaoPayService.payReady(memberId, kakaoPayDto, partner_order_id);

        // 검증을 위한 tid 값 저장
        kakaoPayService.saveSessionTid(session, response, partner_order_id, memberId, kakaoPayDto);

        return response;
    }

    @Transactional
    @GetMapping("/completed")
    public String payCompleted(@RequestParam(PG_TOKEN) String pg_token,
                               @SessionAttribute(name = TID, required = false) String tid,
                               @SessionAttribute(name = KAKAO_ORDER_ID, required = false) String partner_order_id,
                               @SessionAttribute(name = KAKAO_MEMBER_ID, required = false) Long kakaoMemberId,
                               @AuthenticationPrincipal PrincipalDetails user,
                               HttpServletResponse response,
                               HttpSession session) {
        Member member = user.getMember();
        if (tid == null || partner_order_id == null || member == null || !member.getMemberId().equals(kakaoMemberId)) {
            return AlertUtils.alertAndClose(response, "잘못된 결제요청입니다.");
        }
        log.info("결제승인 요청을 인증하는 토큰 : {}", pg_token);
        log.info("주문정보 : {}", partner_order_id);
        log.info("결재고유 번호 : {}", tid);

        ApproveResponse approve = kakaoPayService.payApprove(tid, pg_token, partner_order_id, member.getMemberId());

        Payment payment = Payment.builder()
            .member(member)
            .charge(approve.getTotal())
            .resultCash(member.getMemberCash() + approve.getTotal())
            .cashType(CashEnum.충전)
            .paymentType(PaymentType.카카오페이)
            .build();
        paymentService.save(payment);

        kakaoPayService.deleteSessionId(session);

        execute(response, "충전이 완료되었습니다.");
        return null;
    }
    private String execute(HttpServletResponse response, String alert) {
        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("utf-8");

        String command = "<script> alert(" + alert + "); " + getOption() + " window.self.close(); </script>";
        try (PrintWriter out = response.getWriter()) {
            out.println(command);
            out.flush();
        } catch (IOException e) {
            log.error("Alert IOException 발생! = {}", AlertTemplate.class);
        }
        return null;
    }

    private String getOption() {
        // redirect url이 있으면 url로 이동
        return "const urlParams = new URLSearchParams(opener.location.search); " +
            "let redirect = urlParams.get('url');" +
            "if (redirect == null) redirect = '/';" +
            "opener.location.href=redirect;";
    }

    @GetMapping({"/cancel", "fail"})
    public String payCancel() {
        return "redirect:/";
    }
}

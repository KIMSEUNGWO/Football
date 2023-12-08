package football.start.allOfFootball.controller.api.kakaoPay;

import football.start.allOfFootball.SessionConst;
import football.start.allOfFootball.controller.api.kakaoPay.dto.ApproveResponse;
import football.start.allOfFootball.controller.api.kakaoPay.dto.ReadyResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
@RequiredArgsConstructor
public class KakaoPayService {


    private final KakaoPayRepository kakaoPayRepository;

    public void saveSessionTid(HttpServletRequest request, ReadyResponse response, String partner_order_id, Long memberId) {
        HttpSession session = request.getSession();

        session.setAttribute(SessionConst.TID, response.getTid());
        session.setAttribute(SessionConst.KAKAO_ORDER_ID, partner_order_id);
        session.setAttribute(SessionConst.KAKAO_MEMBER_ID, memberId);
    }

    public ReadyResponse payReady(Long memberId, KakaoPayDto kakaoPayDto, String partnerOrderId) {

        HttpHeaders headers = kakaoPayRepository.getHeaders();

        MultiValueMap<String, String> body = kakaoPayRepository.getPayReadyBody(memberId, kakaoPayDto, partnerOrderId);

        String payReadyUrl = "https://kapi.kakao.com/v1/payment/ready";

        return kakaoPayRepository.getRequestReadyPost(headers, body, payReadyUrl);
    }

    public ApproveResponse payApprove(String tid, String pgToken, String partnerOrderId, Long memberId) {

        HttpHeaders headers = kakaoPayRepository.getHeaders();

        MultiValueMap<String, String> body = kakaoPayRepository.getPayApproveBody(tid, pgToken, partnerOrderId, memberId);

        String approveURL = "https://kapi.kakao.com/v1/payment/approve";

        return kakaoPayRepository.getRequestApprovePost(headers, body, approveURL);
    }

    public void deleteSessionId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(SessionConst.TID);
        session.removeAttribute(SessionConst.KAKAO_MEMBER_ID);
        session.removeAttribute(SessionConst.KAKAO_ORDER_ID);
    }
}

package football.api.kakaopay.service;

import football.api.kakaopay.component.RequestTo;
import football.api.kakaopay.dto.KakaoPayDto;
import football.api.kakaopay.repository.KakaoPayRepository;
import football.common.consts.SessionConst;
import football.api.kakaopay.dto.ApproveResponse;
import football.api.kakaopay.dto.ReadyResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import static football.api.kakaopay.consts.KakaoPayConst.*;

@Service
@RequiredArgsConstructor
public class KakaoPayService {

    private final String PAY_READY_URI = "https://kapi.kakao.com/v1/payment/ready";
    private final String APPROVE_URI = "https://kapi.kakao.com/v1/payment/approve";

    private final RequestTo requestTo;


    private final KakaoPayRepository kakaoPayRepository;

    public void saveSessionTid(HttpSession session, ReadyResponse response, String partner_order_id, Long memberId, KakaoPayDto kakaoPayDto) {

        session.setAttribute(TID, response.getTid());
        session.setAttribute(KAKAO_ORDER_ID, partner_order_id);
        session.setAttribute(KAKAO_MEMBER_ID, memberId);
        session.setAttribute(SessionConst.REDIRECT_URL, kakaoPayDto.getRedirect());
    }

    public void deleteSessionId(HttpSession session) {
        session.removeAttribute(TID);
        session.removeAttribute(KAKAO_MEMBER_ID);
        session.removeAttribute(KAKAO_ORDER_ID);
        session.removeAttribute(SessionConst.REDIRECT_URL);
    }

    public ReadyResponse payReady(Long memberId, KakaoPayDto kakaoPayDto, String partnerOrderId) {

        MultiValueMap<String, String> body = kakaoPayRepository.getPayReadyBody(memberId, kakaoPayDto, partnerOrderId);
        return requestTo.post(PAY_READY_URI, body, ReadyResponse.class);
    }

    public ApproveResponse payApprove(String tid, String pgToken, String partnerOrderId, Long memberId) {

        MultiValueMap<String, String> body = kakaoPayRepository.getPayApproveBody(tid, pgToken, partnerOrderId, memberId);
        return requestTo.post(APPROVE_URI, body, ApproveResponse.class);
    }

}

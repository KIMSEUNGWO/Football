package football.api.kakaopay.repository;

import football.api.kakaopay.dto.CidCode;
import football.api.kakaopay.dto.KakaoPayDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Repository
@Slf4j
@RequiredArgsConstructor
public class KakaoPayRepository {

    public MultiValueMap<String, String> getPayReadyBody(Long memberId, KakaoPayDto kakaoPayDto, String partner_order_id) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", CidCode.TEST);
        parameters.add("partner_order_id", partner_order_id);
        parameters.add("partner_user_id", String.valueOf(memberId));
        parameters.add("item_name", "캐시충전");
        parameters.add("quantity", String.valueOf(1));
        parameters.add("total_amount", String.valueOf(kakaoPayDto.getPrice()));
        parameters.add("tax_free_amount", "0");
        parameters.add("approval_url", "http://localhost:8080/cash/charge/kakao/completed"); // 결제승인시 넘어갈 url
        parameters.add("cancel_url", "http://localhost:8080/cash/charge/kakao/cancel"); // 결제취소시 넘어갈 url
        parameters.add("fail_url", "http://localhost:8080/cash/charge/kakao/fail"); // 결제 실패시 넘어갈 url
        return parameters;
    }

    public MultiValueMap<String, String> getPayApproveBody(String tid, String pgToken, String partnerOrderId, Long memberId) {

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", CidCode.TEST);
        parameters.add("tid", tid);
        parameters.add("partner_order_id", partnerOrderId); // 주문명
        parameters.add("partner_user_id", String.valueOf(memberId));
        parameters.add("pg_token", pgToken);

        return parameters;
    }
}

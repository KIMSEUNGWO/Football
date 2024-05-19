package football.api.kakaopay.repository;

import football.api.kakaopay.dto.CidCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static football.common.consts.Constant.DOMAIN_URI;

@Repository
@RequiredArgsConstructor
public class KakaoPayRepository {

    private final String APPROVAL_URI = DOMAIN_URI + "/cash/charge/kakao/completed";
    private final String CANCEL_URI = DOMAIN_URI + "/cash/charge/kakao/cancel";
    private final String FAIL_URI = DOMAIN_URI + "/cash/charge/kakao/fail";

    private MultiValueMap<String, String> initBody(Long memberId, String partner_order_id) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", CidCode.TEST);
        parameters.add("partner_order_id", partner_order_id); // 주문명
        parameters.add("partner_user_id", String.valueOf(memberId));
        return parameters;
    }

    public MultiValueMap<String, String> getPayReadyBody(Long memberId, int price, String partner_order_id) {
        MultiValueMap<String, String> parameters = initBody(memberId, partner_order_id);
        parameters.add("item_name", "캐시충전");
        parameters.add("quantity", String.valueOf(1));
        parameters.add("total_amount", String.valueOf(price));
        parameters.add("tax_free_amount", "0");
        parameters.add("approval_url", APPROVAL_URI); // 결제승인시 넘어갈 url
        parameters.add("cancel_url", CANCEL_URI); // 결제취소시 넘어갈 url
        parameters.add("fail_url", FAIL_URI); // 결제 실패시 넘어갈 url
        return parameters;
    }

    public MultiValueMap<String, String> getPayApproveBody(String tid, String pgToken, String partner_order_id, Long memberId) {
        MultiValueMap<String, String> parameters = initBody(memberId, partner_order_id);
        parameters.add("tid", tid);
        parameters.add("pg_token", pgToken);
        return parameters;
    }
}

package football.start.allOfFootball.controller.api.kakaoPay;

import football.start.allOfFootball.controller.api.RequestTo;
import football.start.allOfFootball.controller.api.kakaoPay.dto.ApproveResponse;
import football.start.allOfFootball.controller.api.kakaoPay.dto.ReadyResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Repository
@Slf4j
@RequiredArgsConstructor
public class KakaoPayRepository {
    @Value("${kakaoPay.adminKey}")
    private String adminKey;
    private final RequestTo requestTo;

    public HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "KakaoAK " + adminKey);
        return headers;
    }

    public MultiValueMap<String, String> getPayReadyBody(Long memberId, KakaoPayDto kakaoPayDto, String partner_order_id) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", CidCode.TEST);
        parameters.add("partner_order_id", partner_order_id);
        parameters.add("partner_user_id", String.valueOf(memberId));
        parameters.add("item_name", "캐시충전");
        parameters.add("quantity", String.valueOf(1));
        parameters.add("total_amount", String.valueOf(kakaoPayDto.getPrice()));
        parameters.add("tax_free_amount", "0");
        parameters.add("approval_url", "http://localhost:8080/cache/charge/kakao/completed"); // 결제승인시 넘어갈 url
        parameters.add("cancel_url", "http://localhost:8080/cache/charge/kakao/cancel"); // 결제취소시 넘어갈 url
        parameters.add("fail_url", "http://localhost:8080/cache/charge/kakao/fail"); // 결제 실패시 넘어갈 url
        return parameters;
    }

    public ApproveResponse getRequestApprovePost(HttpHeaders headers, MultiValueMap<String, String> body, String payReadyURL) {
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        log.info("전송요청 POST : {}, {}", entity.toString(), this);
        return restTemplate.postForObject(payReadyURL, entity, ApproveResponse.class);
    }

    public MultiValueMap<String, String> getPayApproveBody(String tid, String pgToken, String partnerOrderId, Long memberId) {

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", "TC0ONETIME");
        parameters.add("tid", tid);
        parameters.add("partner_order_id", partnerOrderId); // 주문명
        parameters.add("partner_user_id", String.valueOf(memberId));
        parameters.add("pg_token", pgToken);

        return parameters;
    }

    public ReadyResponse getRequestReadyPost(HttpHeaders headers, MultiValueMap<String, String> body, String payReadyUrl) {
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        log.info("전송요청 POST : {}, {}", entity.toString(), this);
        return restTemplate.postForObject(payReadyUrl, entity, ReadyResponse.class);
    }
}

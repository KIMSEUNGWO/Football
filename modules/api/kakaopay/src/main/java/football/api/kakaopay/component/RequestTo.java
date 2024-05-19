package football.api.kakaopay.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class RequestTo {

    private final RestTemplate restTemplate;
    private final HttpHeaders headers;

    public <T> T post(String uri, MultiValueMap<String, String> body, Class<T> clazz) {
        log.info("전송요청 POST : {}", this);
        return restTemplate.postForObject(uri, new HttpEntity<>(body, headers), clazz);
    }
}

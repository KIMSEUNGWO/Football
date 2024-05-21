package football.api.kakaologin.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class KakaoRequestTo {

    private final RestTemplate restTemplate;
    private final HttpHeaders headers;

    public String postAccessToken(String url, MultiValueMap<String, String> body) {
        return restTemplate.postForObject(url, new HttpEntity<>(body, headers), String.class);
    }

    public String postRequireAccessToken(String url, String accessToken) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        header.set("Authorization", "Bearer " + accessToken);
        return restTemplate.postForObject(url, new HttpEntity<>(header), String.class);
    }

}

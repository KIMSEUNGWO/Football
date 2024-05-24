package football.api.kakaologin.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class KakaoRequestTo {

    private final RestTemplate restTemplate;

    public String post(String url, String accessToken) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        header.set("Authorization", "Bearer " + accessToken);
        return restTemplate.postForObject(url, new HttpEntity<>(header), String.class);
    }

}

package football.start.allOfFootball.controller.api.kakaoLogin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@Configuration
public class KakaoLoginConfig {

    @Bean
    KakaoRequestTo requestTo() {
        return new KakaoRequestTo(restTemplate(), httpHeaders());
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
    @Bean
    HttpHeaders httpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }
}

package football.api.kakaopay.config;

import football.api.kakaopay.component.RequestTo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan(basePackages = "football")
public class KakaoApiConfig {

    @Value("${KAKAO_PAY.ADMIN_KEY}")
    private String ADMIN_KEY;

    @Bean
    RequestTo requestTo() {
        return new RequestTo(restTemplate(), httpHeaders());
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    HttpHeaders httpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "KakaoAK " + ADMIN_KEY);
        return headers;
    }

}

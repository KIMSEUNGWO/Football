package football.api.kakaologin.config;

import football.api.kakaologin.dto.KakaoRequestTo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class KakaoLoginConfig {

    @Bean
    KakaoRequestTo requestTo() { return new KakaoRequestTo(restTemplate()); }

    @Bean
    RestTemplate restTemplate() { return new RestTemplate(); }
}

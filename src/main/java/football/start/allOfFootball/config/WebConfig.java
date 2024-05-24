package football.start.allOfFootball.config;

import football.start.allOfFootball.interceptor.MatchInterceptor;
import football.start.allOfFootball.interceptor.OrderInterceptor;
import football.start.allOfFootball.service.domainService.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final MatchService matchService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new MatchInterceptor(matchService))
                .order(2)
                .addPathPatterns("/match/**", "/order/**");

        registry.addInterceptor(new OrderInterceptor(matchService))
                .order(3)
                .addPathPatterns("/order/**");
    }

}

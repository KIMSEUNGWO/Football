package football.start.allOfFootball.config;

import football.start.allOfFootball.interceptor.LoginInterceptor;
import football.start.allOfFootball.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final LoginRepository loginRepository;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor(loginRepository))
                .order(1)
                .addPathPatterns("/mypage", "/mypage/**", "/admin", "/admin/**")
                .excludePathPatterns("/css/**", "/js/**", "/fonts/**", "/images/**");
    }

}

package football.start.allOfFootball.config;

import football.common.jpaRepository.JpaAdminRepository;
import football.start.allOfFootball.argumentresolver.LoginMemberArgumentResolver;
import football.start.allOfFootball.interceptor.AdminInterceptor;
import football.start.allOfFootball.interceptor.LoginInterceptor;
import football.start.allOfFootball.interceptor.MatchInterceptor;
import football.start.allOfFootball.interceptor.OrderInterceptor;
import football.start.allOfFootball.service.domainService.MatchService;
import football.common.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final MatchService matchService;
    private final MemberService memberService;
    private final JpaAdminRepository jpaAdminRepository;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver(memberService));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginInterceptor(memberService))
//                .order(1)
//                .addPathPatterns("/mypage", "/mypage/**", "/admin", "/admin/**", "/cash/charge", "/order/**", "/manager", "/manager/**")
//                .excludePathPatterns("/css/**", "/js/**", "/fonts/**", "/images/**");
//
//        registry.addInterceptor(new MatchInterceptor(matchService))
//                .order(2)
//                .addPathPatterns("/match/**", "/order/**");
//
//        registry.addInterceptor(new OrderInterceptor(memberService, matchService))
//                .order(3)
//                .addPathPatterns("/order/**");
//
//        registry.addInterceptor(new AdminInterceptor(memberService, jpaAdminRepository))
//                .order(4)
//                .addPathPatterns("/admin", "/admin/**");

    }

}

package football.login.config;

import football.login.config.auth.PrincipalDetailsService;
import football.login.config.auth.SocialLogoutHandler;
import football.login.config.auth.UserRefreshProvider;
import football.login.config.oauth.CustomAuthenticationSuccessHandler;
import football.login.config.oauth.PrincipalOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static football.common.enums.Role.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final SocialLogoutHandler socialLogoutHandler;
    private final PrincipalDetailsService principalDetailsService;
    private final PrincipalOauth2UserService principalOauth2UserService;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Bean
    UserRefreshProvider provider() {
        return new UserRefreshProvider(principalDetailsService);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(10);
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests( request ->
                request
                    .requestMatchers("/mypage/**", "/order/**", "/cash/charge/**").authenticated()
                    .requestMatchers("/manager/**", "/mypage/manager").hasAnyRole(MANAGER.name(), ADMIN.name())
                    .requestMatchers("/admin/**").hasRole(ADMIN.name())
                    .anyRequest().permitAll()
        );

        http.formLogin(formLogin ->
            formLogin
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/")
        );

        http.logout(logout ->
            logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .addLogoutHandler(socialLogoutHandler)
        );

        http.oauth2Login( login ->
            login
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .userInfoEndpoint(endPoint -> endPoint.userService(principalOauth2UserService))
                .successHandler(customAuthenticationSuccessHandler)
                .failureHandler(customAuthenticationFailureHandler)
        );

        return http.build();
    }

}

package football.common.config;

import football.common.config.auth.PrincipalDetailsService;
import football.common.config.auth.SocialLogoutHandler;
import football.common.config.auth.UserRefreshProvider;
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

    @Bean
    UserRefreshProvider provider() {
        return new UserRefreshProvider(principalDetailsService);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests( request ->
                request
                    .requestMatchers("/mypage/**", "/order/**", "/cash/charge/**").authenticated()
                    .requestMatchers("/manager/**").hasAnyRole(MANAGER.name(), ADMIN.name())
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

        return http.build();
    }

}

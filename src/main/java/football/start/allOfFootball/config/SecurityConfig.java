package football.start.allOfFootball.config;

import football.start.allOfFootball.common.BCrypt;
import football.start.allOfFootball.repository.LoginRepository;
import football.start.allOfFootball.repository.LoginRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(10);
    }


}

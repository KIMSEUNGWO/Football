package football.start.allOfFootball;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class, scanBasePackages = "football")
@EnableJpaAuditing
@EnableAspectJAutoProxy
@EnableJpaRepositories(basePackages = "football")
@EnableScheduling
public class AllOfFootballApplication {

	public static void main(String[] args) {
		SpringApplication.run(AllOfFootballApplication.class, args);
	}

}

package football.start.allOfFootball.jpaRepository;

import football.start.allOfFootball.domain.KakaoToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaKakaoTokenRepository extends JpaRepository<KakaoToken, Long> {
}

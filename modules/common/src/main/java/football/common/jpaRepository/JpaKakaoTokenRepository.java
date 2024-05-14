package football.common.jpaRepository;

import football.common.domain.KakaoToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaKakaoTokenRepository extends JpaRepository<KakaoToken, Long> {
}

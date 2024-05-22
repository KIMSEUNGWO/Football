package football.common.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "KAKAO_TOKEN")
@NoArgsConstructor
public class KakaoToken{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long kakaoTokenId;

    @OneToOne
    @JoinColumn(name = "socialId")
    private Social social;

    private String access_token;
    private String refresh_token;

    public KakaoToken(String access_token, String refresh_token) {
        this.access_token = access_token;
        this.refresh_token = refresh_token;
    }

    public void logout() {
        access_token = refresh_token = null;
    }

    public void updateToken(KakaoToken newToken) {
        if (this.access_token == null) this.access_token = newToken.access_token;
        if (this.refresh_token == null) this.refresh_token = newToken.refresh_token;
    }
}

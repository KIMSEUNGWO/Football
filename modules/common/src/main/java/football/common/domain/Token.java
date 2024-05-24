package football.common.domain;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@Table(name = "KAKAO_TOKEN")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Token {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;

    @OneToOne
    @JoinColumn(name = "SOCIAL_ID")
    private Social social;

    private String access_token;
    private String refresh_token;

    public Token(String access_token, String refresh_token) {
        this.access_token = access_token;
        this.refresh_token = refresh_token;
    }

    public void logout() {
        access_token = refresh_token = null;
    }

    public void updateToken(Token newToken) {
        if (this.access_token == null) this.access_token = newToken.access_token;
        if (this.refresh_token == null) this.refresh_token = newToken.refresh_token;
    }

}

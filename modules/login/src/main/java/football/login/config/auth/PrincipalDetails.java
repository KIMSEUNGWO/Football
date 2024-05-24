package football.login.config.auth;

import football.common.domain.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Getter
@Setter
@ToString
public class PrincipalDetails implements UserDetails, OAuth2User {

    private final Member member;
    private final Map<String, Object> attributes;

    public PrincipalDetails(Member member) {
        this.member = member;
        this.attributes = null;
    }

    public PrincipalDetails(Member member, Map<String, Object> attributes) {
        this.member = member;
        this.attributes = attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println("getAuthorities 실행");
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(() -> member.getRole().getRoleName());
        return authorities;
    }

    @Override
    public String getPassword() {
        System.out.println("getPassword");
        return member.getMemberPassword();
    }

    @Override
    public String getUsername() {
        System.out.println("getUsername");
        return member.getMemberEmail();
    }

    public Member getMember() {
        return member;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        System.out.println("getName");
        return member.getMemberName();
    }

    @Override
    public Map<String, Object> getAttributes() {
        System.out.println("getAttributes");
        return attributes;
    }
}

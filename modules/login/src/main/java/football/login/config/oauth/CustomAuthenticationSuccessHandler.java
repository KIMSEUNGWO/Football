package football.login.config.oauth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        String redirect = "<script> const urlParams = new URLSearchParams(opener.location.search); " +
            "let redirect = urlParams.get('url'); " +
            "if (redirect == null) redirect = '/';" +
            "opener.location.href=redirect; window.self.close(); </script>";
        response.setContentType("text/html");
        response.getWriter().write(redirect);
    }
}

package codesquad.shine.support.auth.authentication.handler;

import codesquad.shine.support.auth.authentication.dto.TokenResponse;
import codesquad.shine.support.auth.context.Authentication;
import codesquad.shine.support.auth.token.JwtTokenFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private JwtTokenFactory jwtTokenFactory;

    public TokenAuthenticationSuccessHandler(JwtTokenFactory jwtTokenFactory) {
        this.jwtTokenFactory = jwtTokenFactory;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String accessToken = jwtTokenFactory.createAccessToken(authentication.getPrincipal().toString(), authentication.getAuthorities());
        String refreshToken = jwtTokenFactory.createRefreshToken(authentication.getPrincipal().toString(), authentication.getAuthorities());
        TokenResponse tokenResponse = new TokenResponse(accessToken, refreshToken);

        String responseToClient = new ObjectMapper().writeValueAsString(tokenResponse);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getOutputStream().print(responseToClient);
    }
}

package codesquad.shine.support.auth.authentication.filter;

import codesquad.shine.support.auth.authentication.AuthenticationToken;
import codesquad.shine.support.auth.authentication.dto.RefreshTokenRequest;
import codesquad.shine.support.auth.authentication.handler.AuthenticationFailureHandler;
import codesquad.shine.support.auth.authentication.handler.AuthenticationSuccessHandler;
import codesquad.shine.support.auth.authentication.provider.AuthenticationManager;
import codesquad.shine.support.auth.context.Authentication;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class TokenRefreshInterceptor extends AbstractAuthenticationProcessingFilter {

    private static final String EXPIRE_USER_INFO = "인증 정보가 만료된 회원입니다.";

    public TokenRefreshInterceptor(AuthenticationSuccessHandler successHandler,
                                   AuthenticationFailureHandler failureHandler,
                                   AuthenticationManager authenticationManager) {
        super(successHandler, failureHandler, authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        AuthenticationToken authRequest = convert(request);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    protected AuthenticationToken convert(HttpServletRequest request) throws IOException {
        String content = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        RefreshTokenRequest refreshTokenRequest = new ObjectMapper().readValue(content, RefreshTokenRequest.class);

        String refreshToken = refreshTokenRequest.getRefreshToken();

        return new AuthenticationToken(refreshToken, null);
    }

    @Override
    protected boolean getContinueChainBeforeSuccessfulAuthentication() {
        return false;
    }

    @Override
    protected boolean getContinueChainBeforeUnsuccessfulAuthentication() {
        return false;
    }
}

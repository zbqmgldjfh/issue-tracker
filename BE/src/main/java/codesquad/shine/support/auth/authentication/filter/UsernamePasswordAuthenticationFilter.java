package codesquad.shine.support.auth.authentication.filter;

import codesquad.shine.support.auth.authentication.AuthenticationToken;
import codesquad.shine.support.auth.authentication.handler.AuthenticationFailureHandler;
import codesquad.shine.support.auth.authentication.handler.AuthenticationSuccessHandler;
import codesquad.shine.support.auth.authentication.provider.AuthenticationManager;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class UsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public static final String USERNAME_FIELD = "username";
    public static final String PASSWORD_FIELD = "password";

    public UsernamePasswordAuthenticationFilter(AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler, AuthenticationManager authenticationManager) {
        super(successHandler, failureHandler, authenticationManager);
    }

    @Override
    protected AuthenticationToken convert(HttpServletRequest request) {
        Map<String, String[]> paramMap = request.getParameterMap();
        String username = paramMap.get(USERNAME_FIELD)[0];
        String password = paramMap.get(PASSWORD_FIELD)[0];

        return new AuthenticationToken(username, password);
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

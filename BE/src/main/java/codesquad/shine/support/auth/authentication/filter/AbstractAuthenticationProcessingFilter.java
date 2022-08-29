package codesquad.shine.support.auth.authentication.filter;

import codesquad.shine.support.auth.authentication.handler.AuthenticationFailureHandler;
import codesquad.shine.support.auth.authentication.handler.AuthenticationSuccessHandler;
import codesquad.shine.support.auth.authentication.provider.AuthenticationManager;
import codesquad.shine.support.auth.context.Authentication;
import codesquad.shine.support.auth.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractAuthenticationProcessingFilter implements HandlerInterceptor {

    private AuthenticationSuccessHandler successHandler;
    private AuthenticationFailureHandler failureHandler;
    private AuthenticationManager authenticationManager;

    public AbstractAuthenticationProcessingFilter(AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler, AuthenticationManager authenticationManager) {
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        try {
            Authentication authentication = attemptAuthentication(request, response);
            this.successfulAuthentication(request, response, authentication);
            return getContinueChainBeforeSuccessfulAuthentication();
        } catch (Exception e) {
            unsuccessfulAuthentication(request, response, e);
            return getContinueChainBeforeUnsuccessfulAuthentication();
        }
    }

    private void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        SecurityContextHolder.getContext().setAuthentication(authentication);

        successHandler.onAuthenticationSuccess(request, response, authentication);
    }

    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response, Exception failed) throws IOException {
        failureHandler.onAuthenticationFailure(request, response, failed);
    }

    protected abstract Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException;

    protected AuthenticationManager getAuthenticationManager() {
        return this.authenticationManager;
    }

    protected boolean getContinueChainBeforeSuccessfulAuthentication() {
        return true;
    }

    protected boolean getContinueChainBeforeUnsuccessfulAuthentication() {
        return true;
    }
}

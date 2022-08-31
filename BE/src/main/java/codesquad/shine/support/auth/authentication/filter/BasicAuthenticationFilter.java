package codesquad.shine.support.auth.authentication.filter;

import codesquad.shine.support.auth.authentication.AuthenticationToken;
import codesquad.shine.support.auth.authentication.AuthorizationExtractor;
import codesquad.shine.support.auth.authentication.AuthorizationType;
import codesquad.shine.support.auth.authentication.handler.AuthenticationFailureHandler;
import codesquad.shine.support.auth.authentication.handler.AuthenticationSuccessHandler;
import codesquad.shine.support.auth.authentication.provider.AuthenticationManager;
import codesquad.shine.support.auth.context.Authentication;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BasicAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public BasicAuthenticationFilter(AuthenticationSuccessHandler successHandler,
                                     AuthenticationFailureHandler failureHandler,
                                     AuthenticationManager authenticationManager) {
        super(successHandler, failureHandler, authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        AuthenticationToken authRequest = convert(request);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private AuthenticationToken convert(HttpServletRequest request) {
        String authCredentials = AuthorizationExtractor.extract(request, AuthorizationType.BASIC);
        String authHeader = new String(Base64.decodeBase64(authCredentials));

        String[] splits = authHeader.split(":");
        String principal = splits[0];
        String credentials = splits[1];

        return new AuthenticationToken(principal, credentials);
    }
}

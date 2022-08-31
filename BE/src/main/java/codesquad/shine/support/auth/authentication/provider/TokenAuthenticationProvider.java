package codesquad.shine.support.auth.authentication.provider;

import codesquad.shine.support.auth.authentication.AuthenticationException;
import codesquad.shine.support.auth.authentication.AuthenticationToken;
import codesquad.shine.support.auth.context.Authentication;
import codesquad.shine.support.auth.token.JwtTokenFactory;

import java.util.List;

public class TokenAuthenticationProvider implements AuthenticationManager {

    private final JwtTokenFactory jwtTokenFactory;

    public TokenAuthenticationProvider(JwtTokenFactory jwtTokenFactory) {
        this.jwtTokenFactory = jwtTokenFactory;
    }

    @Override
    public Authentication authenticate(AuthenticationToken authenticationToken) throws AuthenticationException {
        if (!jwtTokenFactory.validateToken(authenticationToken.getPrincipal())) {
            throw new AuthenticationException();
        }

        String principal = jwtTokenFactory.parsePayload(authenticationToken.getPrincipal());
        List<String> roles = jwtTokenFactory.getRoles(authenticationToken.getPrincipal());

        return new Authentication(principal, roles);
    }
}

package codesquad.shine.support.auth.authentication.provider;


import codesquad.shine.support.auth.authentication.AuthenticationException;
import codesquad.shine.support.auth.authentication.AuthenticationToken;
import codesquad.shine.support.auth.context.Authentication;
import codesquad.shine.support.auth.token.JwtTokenProvider;

import java.util.List;

public class TokenAuthenticationProvider implements AuthenticationManager {
    private JwtTokenProvider jwtTokenProvider;

    public TokenAuthenticationProvider(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public Authentication authenticate(AuthenticationToken authenticationToken) throws AuthenticationException {
        if (!jwtTokenProvider.validateToken(authenticationToken.getPrincipal())) {
            throw new AuthenticationException();
        }

        String principal = jwtTokenProvider.getPrincipal(authenticationToken.getPrincipal());
        List<String> roles = jwtTokenProvider.getRoles(authenticationToken.getPrincipal());

        Authentication authentication = new Authentication(principal, roles);
        return authentication;
    }
}

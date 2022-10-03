package codesquad.shine.support.auth.authentication.provider;

import codesquad.shine.support.auth.authentication.AuthenticationException;
import codesquad.shine.support.auth.authentication.AuthenticationToken;
import codesquad.shine.support.auth.context.Authentication;

public class RefreshTokenAuthenticationProvider implements AuthenticationManager {

    @Override
    public Authentication authenticate(AuthenticationToken authentication) throws AuthenticationException {
        String principal = (String) authentication.getPrincipal();
        if (principal.isBlank()) {
            throw new AuthenticationException("인증토큰이 존재하지 않습니다.");
        }

        return new Authentication(principal, null, null);
    }
}

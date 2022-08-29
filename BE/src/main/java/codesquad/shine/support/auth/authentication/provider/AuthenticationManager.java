package codesquad.shine.support.auth.authentication.provider;

import codesquad.shine.support.auth.authentication.AuthenticationException;
import codesquad.shine.support.auth.context.Authentication;

public interface AuthenticationManager {
    Authentication authenticate(Authentication authentication) throws AuthenticationException;
}
